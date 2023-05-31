package guiao6;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface IWarehouse {
  void supply(String product, int quantity);

  void consume(String[] items) throws InterruptedException;
}

class Warehouse implements IWarehouse {
  HashMap<String, Record> stock = new HashMap<>();
  Lock l = new ReentrantLock();

  class Record {
    public int quantity = 0;
    public Condition cond = l.newCondition();

    void add(int x) {
      quantity += x;
      cond.signalAll();
    }

    void remove() throws InterruptedException {
      while (quantity < 1)
        cond.await();
      quantity--;
    }
  }

  public Warehouse() {
  }

  public void supply(String product, int quantity) {
    l.lock();
    try {
      if (!stock.containsKey(product)) {
        Record r = new Record();
        stock.put(product, r);
      } else {
        stock.get(product).add(quantity);
      }
    } finally {
      l.unlock();
    }
  }

  public void consume(String[] items) throws InterruptedException {
    for (String item : items) {
      l.lock();
      try {
        if (!stock.containsKey(item)) {
          Record temp = new Record();
          stock.put(item, temp);
        } else {
          stock.get(item).remove();
        }
      } finally {
        l.unlock();
      }
    }
  }

  public static void main(String args[]) {
    Warehouse w = new Warehouse();

    new Thread(() -> {
        try {
            System.out.println("Consuming martelos");
            String items[] = {"martelo", "martelo"};
            w.consume(items);
            System.out.println("Consumed 2 martelos");
        } catch (Exception e) {}
    }).start();

    new Thread(() -> {
        try {
            System.out.println("Consuming pcs");
            String items[] = {"pc", "pc", "pc", "pc", "pc"};
            w.consume(items);
            System.out.println("Consumed 5 pcs");
        } catch (Exception e) {}
    }).start();

    new Thread(() -> {
        try {
            System.out.println("Supplying martelos");
            w.supply("martelo", 10);
            System.out.println("Supplied 10 martelos");
        } catch (Exception e) {}
    }).start();

    new Thread(() -> {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(500);
                System.out.println("Supplying pcs");
                w.supply("pc", 1);
                System.out.println("Supplied 1 pcs");
            }
        } catch (Exception e) {}
    }).start();
}
}
