package guiao4;

import java.util.concurrent.Semaphore;

class BoundedBuffer<T> {
    private int[] buf;
    private int iget = 0;
    private int iput = 0;
    Semaphore items;
    Semaphore slots;
    Semaphore mutget = new Semaphore(1); // semaforo para exclusão mutua
    Semaphore mutput = new Semaphore(1); // não devem estar dois gets a correr ao mesmo tempo

    public BoundedBuffer(int N) {
        this.buf = new int[N];
        items = new Semaphore(0);
        slots = new Semaphore(N);
    }

    public int get() throws InterruptedException {
        int res;
        items.acquire();
        mutget.acquire();
        res = buf[iget];
        iget = (iget+1) % buf.length;
        mutget.release();
        slots.release();
        return res;
    }

    public void put(int v) throws InterruptedException {
        slots.acquire();
        mutput.acquire();
        buf[iput] = v;
        iput = (iput+1) % buf.length;
        mutput.release();
        items.release();
    }
}

class Barreira {
    private final int N;
    private int c=0; // contador
    Thread[] t;
    private Semaphore mut = new Semaphore(1);
    private Semaphore block = new Semaphore(0);

    Barreira (int N) {
        this.N = N;
        this.t = new Thread[N];
    }

    void await() throws InterruptedException {
        mut.acquire();
        this.c+=1;
        int v = c;
        if (v < N) {
            block.acquire();
        } else {
            for(int i=0;i<N-1;++i) {
                block.release();
            }
        }

        mut.release();
    }
}

public class Main {
    public static void main(String[] args) {
        BoundedBuffer b = new BoundedBuffer(20);

        // Produtor
        new Thread(() -> {
            try {
                for (int i = 0; ; ++i) {
                    System.out.println("vou fazer put de: " + i);
                    b.put(i);
                    System.out.println("fiz put de: " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {}
        }).start();

        // Consumidor
        new Thread(() -> {
            try {
                for (int i = 0; ; ++i) {
                    System.out.println("vou fazer get");
                    int j = b.get();
                    System.out.println("get retornou: " + j);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {}
        }).start();

    }
}
