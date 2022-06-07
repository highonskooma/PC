package com.company;

public class Player extends Avatar{
    Integer size;

    public Player() {
        super();
        this.size = 0;
    }

    public Player(Integer id, String cor, Integer size) {
        super(id,cor);
        this.size = size;
    }
}
