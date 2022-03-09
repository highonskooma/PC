package com.company;

import java.util.concurrent.ThreadLocalRandom;

public class Account {
    private int saldo;

    public Account() {
        this.saldo = 0;
    }

    public int balance() { return this.saldo; }
    public void deposit(int val) { this.saldo += val; }
    public void withdraw(int val) throws NotEnoughFunds {
        if (this.saldo < val) throw new NotEnoughFunds();
        this.saldo -= val;
    }

    public static class NotEnoughFunds extends Exception {}

}
