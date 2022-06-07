package com.company;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {

    public static void main(String[] args) {
	    PApplet.main("com.company.Main", args);
    }

    class Target {
        float tx, ty;
        Target(){
            tx = random(1000);//random x axis position
            ty = random(1000);//random y axis position
        }
        void draw(){
            stroke(100);
            ellipse(tx,ty, radius, radius);//target's location and size
        }
    }

    float x, y;
    float easing = (float) 0.05;
    PVector circle = new PVector(250, 250);
    int radius = 10;
    int lastTargetSpawn;
    int spawnDeltaTime=1000;
    ArrayList<Target> target = new ArrayList<Target>();

    public void settings() {
        size(900, 900);
    }

    public void setup() {
        x = y = width/2;
        noStroke();
        smooth();
        lastTargetSpawn=millis();
    }

    void spawnTarget() {
        target.add(new Target());
        ellipse(mouseX, mouseY, 24, 24);
        lastTargetSpawn = millis();
    }

    public void draw () {
        background(51);
        fill(255);
        for (int i = 0; i < target.size(); i++) {
            float x_value = Math.abs(x-target.get(i).tx);
            float y_value = Math.abs(y-target.get(i).ty);
            float size = x_value * y_value;
            if ( size < 10 ) {
                target.remove(i);
            }
            target.get(i).draw();
        }
        text(nf(frameRate, 1, 1)+" FPS", 10, 10);   // show your BEAT

        PVector m = new PVector(mouseX, mouseY);

        x = x + (m.x - x) * easing;
        y = y + (m.y - y) * easing;

        fill(255, 0, 0);
        ellipse(x, y, 24, 24);
        ellipse(m.x, m.y, 12, 12);



        if (millis() - lastTargetSpawn > spawnDeltaTime) {
            System.out.println(lastTargetSpawn);
            spawnTarget();
        }
    }


}
