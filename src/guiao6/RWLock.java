package guiao6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface IRWLock {
  void readLock() throws InterruptedException;
  void readUnlock();
  void writeLock() throws InterruptedException;
  void writeUnlock();
}

public class RWLock implements IRWLock {
  private int reading = 0;
  private int writing = 0;
  private Condition readLock, writeLock;
  private Lock l;

  public RWLock() {
    this.l = new ReentrantLock();
    this.readLock = l.newCondition();
    this.writeLock = l.newCondition();
  }

  public void readLock() throws InterruptedException {
    l.lock();
    try {
      while (writing > 0) writeLock.await();
      reading++;
    } finally {
      l.unlock();
    }
  }

  public void readUnlock() {
    l.lock();
    try {
      if (reading > 0) {
        reading--;
        readLock.signalAll();
      }
    } finally {
      l.unlock();
    }
  }

  public void writeLock() throws InterruptedException {
    l.lock();
    try {
      while (reading > 0 && writing > 0) writeLock.await();
      writing++;
    } finally {
      l.unlock();
    }
  }

  public void writeUnlock() {
    l.lock();
    try {
      if (writing > 0) {
        writing--;
        writeLock.signalAll();
      }
    } finally {
      l.unlock();
    }
  }
}
