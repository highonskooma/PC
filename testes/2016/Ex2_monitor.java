import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Monitor {
  Lock lock = new ReentrantLock();
  Condition notThere = lock.newCondition();

  int tipo1, tipo2, n1, n2;

  public Monitor() {
    tipo1 = 1;
    tipo2 = 2;
    n1 = 0;
    n2 = 0;
  }

  synchronized void sinaliza(int tipo) {
    lock.lock();
    try {
      if (tipo == tipo1) {n1 += 1;}
      if (tipo == tipo2) {n2 += 1;}
    } finally {
      lock.unlock();
    } 
  }

  synchronized void espera(int tipo1, int n1, int tipo2, int n2) throws InterruptedException {
    lock.lock();
    try{
      System.out.println("Waitng...");

      while (!(this.n1 > this.n2) || !(this.n1 == n1 && this.n2 == n2)) {
        notThere.await();
      }

      System.out.println("Done waiting");
    } finally {
      lock.unlock();
    }
  }
}

public class Ex2_monitor {
  public static void main(String args[]) {
    Monitor evts = new Monitor();

        new Thread (() -> {
            try{
                evts.espera(1, 2, 2, 1);
            } catch (Exception e) { }
        }).start();


        new Thread (() -> {
            try {
                System.out.println("vou sinalizar 1 1x");
                evts.sinaliza(1);
                Thread.sleep(3000);

                System.out.println("vou sinalizar 2 1x");
                evts.sinaliza(2);
                
                System.out.println("vou sinalizar 1 2x");
                evts.sinaliza(1);
                Thread.sleep(3000);

                
            } catch (Exception e ) { }
        }).start();

  }
}

