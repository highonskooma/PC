package com.company;
import processing.core.PApplet;
import processing.core.PVector;

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
    boolean locked = true;
    int lastTargetSpawn;
    int spawnDeltaTime=1000;
    ArrayList<Cristal> target = new ArrayList<Cristal>();
    Player p = new Player(this,p_size);
    GreetClient client = new GreetClient(); // socket to send info to other players
    Socket sock = new Socket("127.0.0.1",8091); // socket to receive info from other players



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

    public void setup() {
        frameRate(30);
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
    }

    void spawnTarget() {
        Cristal c = new Cristal(this);
        target.add(c);
        lastTargetSpawn = millis();
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
            Thread[] ts = new Thread[2];
            ts[0] = new Thread(() -> {
                try {
                    client.sendMessage(p.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            ts[1] = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    String[] tkn = in.readLine().split(" ");
                    if (!tkn[0].equals(p.getNome())) {
                        ellipse(Float.parseFloat(tkn[1]),Float.parseFloat(tkn[2]),24,24);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            for (int i = 0; i < 2; i++) ts[i].start();
            for (int i = 0; i < 2; i++) ts[i].join();
        } catch (InterruptedException e) { e.printStackTrace(); }



        if (millis() - lastTargetSpawn > spawnDeltaTime) {
            //System.out.println(lastTargetSpawn+" "+ target.toString());
            spawnTarget(); // spawn crystal every deltaTime
        }
    }
}
