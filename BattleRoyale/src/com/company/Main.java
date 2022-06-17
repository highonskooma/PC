package com.company;
import processing.core.PApplet;
import processing.core.PVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main extends PApplet {
    public static void main(String[] args) {
	    PApplet.main("com.company.Main", args);
    }

    float x, y;
    float easing = (float) 0.05;
    //PVector circle = new PVector(250, 250);
    //int radius = 10;
    int p_size = 24;
    //int mult = 2;
    boolean locked = true;
    int lastTargetSpawn;
    int spawnDeltaTime=1000;
    ArrayList<Cristal> target = new ArrayList<Cristal>();
    Player p = new Player(this,p_size,"blackgaze");
    GreetClient client = new GreetClient();


    public void settings() {
        size(1600, 900);
    }

    public void mousePressed() {
        locked = false;
        fill(255, 255, 255);
    }

    public void mouseReleased() {
        locked = true;
    }

    public void setup() {
        x = y = width/2;
        noStroke();
        smooth();
        lastTargetSpawn=millis();
        try {
            client.startConnection("127.0.0.1",8091);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            client.sendMessage(p.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (millis() - lastTargetSpawn > spawnDeltaTime) {
            //System.out.println(lastTargetSpawn+" "+ target.toString());
            spawnTarget(); // spawn crystal every deltaTime
        }
    }
}
