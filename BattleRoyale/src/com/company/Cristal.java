package com.company;

public class Cristal extends Avatar  {

    public Cristal(Main m) {
        super(m);
    }

    public Cristal() {
        super();
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

}
