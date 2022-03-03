package com.company;

class Counter1{
	private int i;
	public void increment() {i+=1;}
	public int value() {return i;}
}

class Incrementador extends Thread {
	final Counter c;
	final int it;
	Incrementador (int it, Counter c) {this.it=it; this.c=c;}
	public void run(){
		for (int i =0; i<it; i++)
			c.increment();
	}
}


class Ex2 {
	public static void main(String[] args) throws InterruptedException {

		final int N = Integer.parseInt(args[0]);
		final int I = Integer.parseInt(args[1]);

		Thread[] ts = new Thread[N]; 
		Counter c = new Counter();

		for (int i=0; i<N; i++) ts[i] = new Incrementador(I, c); 
		for (int i=0; i<N; i++)	ts[i].start();
		for (int i=0; i<N; i++) ts[i].join();
		
		System.out.println(c.value());
	}
}
