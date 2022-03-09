package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
/*
class Depositor extends Thread {
    final int iterations;
    final Banco b;

    public Depositor (int iterations, Banco b) { this.iterations = iterations; this.b = b; }

    public void run() {
        for (int i=0; i<iterations; i++) {
            try {
                b.deposit(i % b.getAccounts().length, 1);
            } catch (Banco.InvalidAccount e) {
                e.printStackTrace();
            }
        }
    }
}
*/
class Observer extends Thread {
    private final Banco b;
    private final int iterations;

    public Observer(Banco b,int it) {this.b=b;this.iterations=it;}

    public void run() {
        int total = 0;
        try {
            total = b.totalBalance();
            for (int i=0; i<iterations; i++) {
                if (b.totalBalance() != total) System.out.println("erro: saldo incorreto ("+b.totalBalance()+")");
            }
        } catch (Banco.InvalidAccount e) {
            e.printStackTrace();
        }
    }
}

class Run {
    public static void main(String[] args) throws InterruptedException, Banco.InvalidAccount, Account.NotEnoughFunds {
        final int N = Integer.parseInt(args[0]); // Numero de Contas
        final int I = Integer.parseInt(args[1]); // Numero de Threads

        Banco b = new Banco(N);
        Thread[] a = new Thread[I];
        Observer obs = new Observer(b,N);


        for (int i=0; i<N; i++) { b.deposit(i,100000); }

        for (int i=0; i<N; i++) { b.withdraw(i,500); }

        System.out.println("total balance: "+b.totalBalance());

        for (int i=0; i<1000; i++) {
            int from = ThreadLocalRandom.current().nextInt(1, N );
            int to = ThreadLocalRandom.current().nextInt(1, N );
            b.transfer(from,to,500);
        }

        System.out.println("total balance: "+b.totalBalance());

        obs.run();

    }
}