package com.company;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class Main extends PApplet {
    public Main() throws IOException {
    }

    public static void main(String[] args) {
        PApplet.main("com.company.Main", args);
    }

    float x, y, x1, y1;
    float easing = (float) 0.05;
    //PVector circle = new PVector(250, 250);
    //int radius = 10;
    int p_size = 24;
    //int mult = 2;
    int n_players;
    boolean locked = true;
    int lastTargetSpawn;
    int spawnDeltaTime=1000;
    ArrayList<Cristal> target = new ArrayList<Cristal>();
    Player p = new Player(this,p_size);
    GreetClient client = new GreetClient(); // socket to send info to other players
    //Socket sock = new Socket("127.0.0.1",8091); // socket to receive info from other players



    public void settings() {
        size(600, 600);
    }

    public void mousePressed() {
        locked = false;
        fill(255, 255, 255);
    }

    public void mouseReleased() {
        locked = true;
    }

    void closeWindow(){
        Frame frame = ( (PSurfaceAWT.SmoothCanvas) ((PSurfaceAWT)surface).getNative()).getFrame();
        //delay(2000);
        frame.dispose();
        System.out.println("GAME OVER.");
        //noLoop();
    };

    void draw_gameover() {
        background(128,0,0);
        textAlign(CENTER);
        text("\n\n\nGAME OVER\nVotre score : ", 320, 180);
        fill(255);
        textAlign(CENTER);
        closeWindow();
    }
    public void setup() {
        frameRate(60);
        x = y = width/2;
        noStroke();
        smooth();
        lastTargetSpawn=millis();
        try {
            client.startConnection("127.0.0.1",8091);
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.setNome(args[0]);
        n_players=Integer.parseInt(args[1]);
    }

    void spawnTarget() {
        Cristal c = new Cristal(this);
        target.add(c);
        lastTargetSpawn = millis();
    }

    // verifica de c1 ganha a c2
    boolean colorWinner(Integer c1, Integer c2) {
        switch (c1) {
            case 0: // verde
                if (c2==1) return true;
                break;
            case 1: // azul
                if (c2==2) return true;
                break;
            case 2: // vermelho
                if (c2==0) return true;
                break;
        }
        return false;
    }

    @Override
    public void exitActual() {
    }
    public void draw () {
        background(51);
        fill(255);
        text(nf(frameRate, 1, 1)+" FPS", 10, 10);   // show FPS

        for (Iterator<Cristal> iterator = target.iterator(); iterator.hasNext(); ) {
            Cristal t = iterator.next();
            if ( dist(t.getX(),t.getY(),p.getX(),p.getY()) < p.getSize()) {
                iterator.remove(); // eat crystal
                p.setSize(p.getSize() + 5); // size amount to increase
                p.setCor(t.getCor());
            }
            else {
                t.draw();
            }
        }

        p.draw();
        try {
            Thread[] ts = new Thread[n_players];
            ts[0] = new Thread(() -> {
                try {
                    client.sendMessage(p.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            // uma thread para cada jogador
            for(int i=1;i<n_players;i++) {
                ts[i] = new Thread(() -> {
                    try {
                        //BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        String[] tkn = client.listener().split(" ");
                        Float t_x = Float.parseFloat(tkn[1]);
                        Float t_y = Float.parseFloat(tkn[2]);
                        Integer t_size = Integer.parseInt(tkn[3]);
                        Integer t_cor = Integer.parseInt(tkn[4]);

                        if (dist(t_x, t_y, p.getX(), p.getY()) < t_size && (p.getSize() < t_size) && colorWinner(t_cor, p.getCor()) ) {
                            draw_gameover();
                            //delay(100);
                            //closeWindow();
                        } else {
                            p.draw();
                        }
                        switch (t_cor) {
                            case 0 -> fill(0, 204, 102); // verde
                            case 1 -> fill(0, 153, 255); // azul
                            case 2 -> fill(255, 0, 0); // vermelho
                        }
                        ellipse(Float.parseFloat(tkn[1]), Float.parseFloat(tkn[2]), Integer.parseInt(tkn[3]), Integer.parseInt(tkn[3]));
                        fill(0);
                        text(tkn[0], t_x, t_y);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            for (int i = 0; i < n_players; i++) ts[i].start();
            for (int i = 0; i < n_players; i++) ts[i].join();
        } catch (InterruptedException e) { e.printStackTrace(); }



        if (millis() - lastTargetSpawn > spawnDeltaTime) {
            //System.out.println(lastTargetSpawn+" "+ target.toString());
            spawnTarget(); // spawn crystal every deltaTime
        }
    }
}
