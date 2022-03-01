package com.company;

public class MyThread extends Thread {
    public void run() {
        try {
            System.out.println("Hello World");
            Thread.sleep(500);
            sleep(500);
            System.out.println("Hello World");
        } catch (InterruptedException ignored) {
        }
    }
    class Myrunneble implements Runnable{
        public void run(){
            System.out.println("Runnable");
        }
    }
}
