package guiao5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer<T> {
  T buffer[];
  int iput = 0, iget = 0;
  private int nelems = 0;

  BoundedBuffer(int N) {
    buffer = (T[]) new Object[N];
  }

  public synchronized T get() throws InterruptedException {
    while (nelems == 0)
      wait();
    T res = buffer[iget];
    nelems--;
    iget = (iget + 1) % buffer.length;
    notifyAll();
    return res;
  }

  public synchronized void put(T x) throws InterruptedException {
    while (!(nelems < buffer.length))
      wait();
    buffer[iput] = x;
    iput = (iput + 1) % buffer.length;
    nelems++;
    notifyAll();
  }
}

interface MatchMaker {
  BoundedBuffer waitForConsumer();

  BoundedBuffer waitForProducer();
}

class MatchMakerImpl implements MatchMaker {
  private int producers = 0;
  private int consumers = 0;
  private BoundedBuffer buffer;
  private Lock lock;

  public MatchMakerImpl() {
    this.buffer = new BoundedBuffer(5);
    lock = new ReentrantLock();
  }

  public BoundedBuffer waitForConsumer() {
    lock.lock();
    try {
      consumers++;
      if (producers > 0) {
        producers--;
        return buffer;
      } else {
        return null;
      }
    } finally {
      lock.unlock();
    }
  }

  public BoundedBuffer waitForProducer() {
    lock.lock();
    try {
      producers++;
      if (consumers > 0) {
        consumers--;
        return buffer;
      } else {return null;}
    } finally {
      lock.unlock();
    }
  }

  public class Monitores {
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
            Thread.sleep(2000);
          }
        } catch (Exception e) {
        }
      });

      consumidor.start();
      produtor.start();
    }
  }
}