package com.company;
import java.util.Scanner;

class Counter{
    private int value;
    void increment() { value += 1; }
    int value() { return value; }
}

class Thread_Inc extends Thread {
    final int j;
    final Counter c;

    Thread_Inc(int k, Counter c){
        this.j = k;
        this.c = c;
    }

    public void run(){
        for (int i=0;i<j;i++){
            // controlo de acesso à variavel partilhada
            synchronized (c) {
                c.increment();
            }
        }
    }
}

class Thread_Inc_v2 extends Thread {
    public int count; //counter
    final int it;  // number of iterations per thread

    Thread_Inc_v2(int k, int c) {
        this.it = k;
        this.count = c;
    }

    public void run(){
        for (int i=0;i<it;i++){
            System.out.println("count: "+count);
            count+=1;
        }
    }
}

class Main{
    public static void main(String[] args) throws InterruptedException{
        //MyThread mt = new MyThread();
        //mt.run();

        Counter c = new Counter();
        Scanner s = new Scanner(System.in);
        System.out.println("Numero de iteraçoes por Thread");
        int j = s.nextInt();
        System.out.println("Numero de Threads: ");
        int k = s.nextInt();
        s.close();
        Thread[] a = new Thread[k];

        for (int i=0;i<k;++i){
            a[i] = new Thread_Inc(j,c);
        }
        for (int i=0;i<k;++i){
            a[i].start();
            System.out.println(c.value());
        }
        for (int i=0;i<k;++i){
            a[i].join();
        }

        System.out.println(c.value());
    }

}

