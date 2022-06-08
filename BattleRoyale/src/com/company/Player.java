package com.company;

import processing.core.PVector;

public class Player extends Avatar{
    Integer size;

    public Player(Main m, Integer size) {
        super(m);
        this.size = size;
    }

    public Player() {
        super();
    }

    public void settings() {
        size(900, 900);
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
        switch (this.cor) {
            case 0 -> m.fill(0, 204, 102);
            case 1 -> m.fill(0, 153, 255);
            case 2 -> m.fill(255, 0, 0);
        }

        m.ellipse(x, y, this.size, this.size);

    }

    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getSize() {
        return size;
    }

}
