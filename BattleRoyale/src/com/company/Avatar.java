package com.company;
import processing.core.PApplet;

import java.io.Serializable;

public class Avatar extends PApplet implements Serializable {
    Main m;
    int cor;
    float x, y;

    public Avatar(Main m) {
        this.m = m;
        this.x = m.random(600);
        this.y = m.random(600);
        this.cor = (int) random(3);
    }

    Avatar(){
        x = random(1000);
        y = random(1000);
        cor = 0;
    }


    public void setCor(int cor) {
        this.cor = cor;
    }

    public int getCor() {
        return cor;
    }

    public float getX() {return this.x;}
    public float getY() {return this.y;}

    @Override
    public String toString() {
        return ("x:"+this.x+
                " , y: "+ this.y);
    }

}
