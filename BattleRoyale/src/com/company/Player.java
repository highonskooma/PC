package com.company;

import processing.core.PVector;

public class Player extends Avatar{
    Integer size;
    String name;

    public Player(Main m, Integer size) {
        super(m);
        this.size = size;
        this.name="";
    }

    public Player() {
        super();
    }


    public void setup() {
        x = y = width/2;
        noStroke();
        smooth();
    }

    public void draw() {
        float targetX = m.mouseX;
        float dx = targetX - x;
        x += dx * 0.05;

        float targetY = m.mouseY;
        float dy = targetY - y;
        y += dy * 0.05;

        m.fill(255);
        m.ellipse(m.mouseX, m.mouseY, 12, 12);
        m.strokeWeight(2);
        switch (this.cor) {
            case 0 -> m.stroke(0, 204, 102);
            case 1 -> m.stroke(0, 153, 255);
            case 2 -> m.stroke(255, 0, 0);
        }
        if(!m.locked) {
            if (this.size>15)
                this.size -= 1;
        }
        m.ellipse(x, y, this.size, this.size);
        m.fill(0);
        m.text(this.name, x-25, y);

    }

    @Override
    public String toString() {
        return (this.name+
                " x:"+this.x+
                " , y: "+ this.y);
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getSize() {
        return size;
    }

    public void setNome(String arg) {
        this.name = arg;
    }
}
