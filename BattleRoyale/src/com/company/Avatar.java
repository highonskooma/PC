package com.company;
import processing.core.PApplet;

public class Avatar extends PApplet {
    Main m;
    int cor;
    float x, y;

    public Avatar(Main m) {
        this.m = m;
        this.x = m.random(1000);
        this.y = m.random(1000);
        this.cor = (int) random(3);
    }

    Avatar(){
        x = random(1000);
        y = random(1000);
        cor = 0;
    }

    public void draw(){
        m.stroke(100);
        switch (this.cor) {
            case 0 -> m.fill(0, 204, 102);
            case 1 -> m.fill(0, 153, 255);
            case 2 -> m.fill(255, 0, 0);
        }
        m.ellipse(x,y, 10, 10); //target's location and size
    }

    public float getX() {return this.x;}
    public float getY() {return this.y;}

    @Override
    public String toString() {
        return ("x:"+this.x+
                " , y: "+ this.y);
    }

}
