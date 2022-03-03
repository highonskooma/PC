package com.company;

import java.util.ArrayList;
import java.util.List;

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

class Run {
    public static void main(String[] args) throws InterruptedException, Banco.InvalidAccount, Account.NotEnoughFunds {
        final int N = Integer.parseInt(args[0]); // Numero de Contas
        final int I = Integer.parseInt(args[1]); // Numero de Threads

        Banco b = new Banco(N);
        Thread[] a = new Thread[I];

        for (int i=0; i<N; i++) { b.deposit(i,1000); }

        for (int i=0; i<N; i++) { b.withdraw(i,500); }

        System.out.println("total balance: "+b.totalBalance());
        System.out.println("id 1: "+b.get(1).balance());
        System.out.println("id 2: "+b.get(2).balance());
        b.transfer(1,2,500);
        System.out.println("id 1: "+b.get(1).balance());
        System.out.println("id 2: "+b.get(2).balance());







    }
}