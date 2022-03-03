package com.company;

class Printer extends Thread {
	final int it;
	Printer(int it){this.it=it;}

	public void run(){
		for (int i=1; i<=it; ++i)
			System.out.println(i);
	}

}


class Ex1 {
	public static void main(String[] args) {

		final int N = Integer.parseInt(args[0]);
		final int I = Integer.parseInt(args[1]);

		for(int i=0;i<N;++i) new Printer(I).start();
	}
}
