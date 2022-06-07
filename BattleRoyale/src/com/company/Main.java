package com.company;

import processing.core.PApplet;

public class Main extends PApplet {

    public static void main(String[] args) {
	    PApplet.main("com.company.Main", args);
    }

    public void settings() {
        size(1600 , 900);
    }

    public void draw() {
        if (mousePressed) {
            fill(0);
        } else {
            fill(255);
        }
        ellipse(mouseX, mouseY, 40, 40);
    }

}
