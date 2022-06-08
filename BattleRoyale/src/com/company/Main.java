package com.company;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main extends PApplet {
    public static void main(String[] args) {
	    PApplet.main("com.company.Main", args);
    }

    class Target {
        float tx, ty;
        int color;
        Target(){
            tx = random(1000);//random x axis position
            ty = random(1000);//random y axis position
            color = (int) random(3);
        }
        void draw(){
            stroke(100);
            switch (this.color) {
                case 0: fill(0,204,102); break;
                case 1: fill(0,153,255); break;
                case 2: fill(255,0,0); break;
            }
            ellipse(tx,ty, radius, radius);//target's location and size
        }

        public float getX() {return this.tx;}
        public float getY() {return this.ty;}

        @Override
        public String toString() {
            return ("x:"+this.tx+
                    " , y: "+ this.ty);
        }
    }

    float x, y;
    float easing = (float) 0.05;
    PVector circle = new PVector(250, 250);
    int radius = 10;
    int p_size = 24;
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
        lastTargetSpawn = millis();
    }

    public void draw () {
        background(51);
        fill(255);
        text(nf(frameRate, 1, 1)+" FPS", 10, 10);   // show FPS
        for (Iterator<Target> iterator = target.iterator(); iterator.hasNext(); ) {
            Target t = iterator.next();
            float x_value = Math.abs(x-t.getX());
            float y_value = Math.abs(y-t.getY());
            float size = x_value * y_value; // diferença absoluta entre as coords do cristal e player
            if ( size < p_size ) {
                iterator.remove(); // eat crystal
                p_size += 5; // size amount to increase
            }
            t.draw();
        }

        PVector m = new PVector(mouseX, mouseY);

        x = x + (m.x - x) * easing;
        y = y + (m.y - y) * easing;

        fill(255, 0, 0);
        ellipse(x, y, p_size, p_size);
        ellipse(m.x, m.y, 12, 12);

        if (millis() - lastTargetSpawn > spawnDeltaTime) {
            //System.out.println(lastTargetSpawn+" "+ target.toString());
            spawnTarget(); // spawn crystal every deltaTime
        }
    }
}
