package guiao6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T> {
  T buffer[];
  int iput = 0, iget = 0;
  private int nelems = 0;
  private Lock lock;
  private Condition notFull, notEmpty;

  BoundedBuffer(int N) {
    buffer = (T[]) new Object[N];
    this.lock = new ReentrantLock();
    this.notFull = lock.newCondition();
    this.notEmpty = lock.newCondition();
  }

  public synchronized T get() throws InterruptedException {
    lock.lock();
    try {
      while (nelems == 0)
        notEmpty.await();
      T res = buffer[iget];
      nelems--;
      iget = (iget + 1) % buffer.length;
      notFull.signal();
      return res;
    } finally {
      lock.unlock();
    }
  }

  public synchronized void put(T x) throws InterruptedException {
    lock.lock();
    try {
      while (!(nelems < buffer.length))
        notFull.await();
      buffer[iput] = x;
      iput = (iput + 1) % buffer.length;
      nelems++;
      notEmpty.signal();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String args[]) throws InterruptedException {
    BoundedBuffer<Integer> buffer = new BoundedBuffer<Integer>(5);

    Thread produtor = new Thread(() -> {
      try {
        int i = 0;
        while (true) {
          System.out.println("Putting " + i);
          buffer.put(i++);
          Thread.sleep(200);
        }
      } catch (Exception e) {
      }
    });

    Thread consumidor = new Thread(() -> {
      try {
        while (true) {
          System.out.println("Getting");
          System.out.println("x = " + buffer.get());
          Thread.sleep(200);
        }
      } catch (Exception e) {
      }
    });

    consumidor.start();
    produtor.start();
  }
}
