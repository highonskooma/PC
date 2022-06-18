package com.company;

public class Partida {
    Integer n_jogadores;
    Integer max_n_jogadores;
    private int round = 0;

    public synchronized void await() throws InterruptedException {

        n_jogadores++;

        int r = round;

        if (n_jogadores > 3) {
            notifyAll();
            System.out.println("notifyAll");

            n_jogadores = 0;
            round++;

            //Thread.sleep(1000);
        }

        while (r == round)
            wait();
        System.out.println("Wait over");
    }
}
