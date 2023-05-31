package exames.EE2016;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface IPonte {
  void inicioTravessiaIda();

  void inicioTravessiaVolta();

  void fimTravessia();
}

public class Ponte implements IPonte {
  private int maxPessoas;
  private int currentPessoas = 0;
  private int pessoasVolta = 0;
  private Lock lock;
  private Condition canCross;

  public Ponte(int maxPessoas) {
    this.maxPessoas = maxPessoas;
    this.lock = new ReentrantLock();
    this.canCross = lock.newCondition();
  }

  public void inicioTravessiaIda() {
    lock.lock();
    try {
      while (currentPessoas == maxPessoas || pessoasVolta > 0) {
        canCross.await();
      }
      currentPessoas++;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return;
    } finally {
      pessoasVolta++;
      lock.unlock();
    }
  }

  public void inicioTravessiaVolta() {
    lock.lock();
    try {
      while (currentPessoas == maxPessoas) {
        canCross.await();
      }
      currentPessoas++;
      pessoasVolta--;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      return;
    } finally {
      lock.unlock();
    }
  }

  public void fimTravessia() {
    lock.lock();
    try {
      currentPessoas--;
      if (currentPessoas == 0) {
        pessoasVolta = 0;
      }
      canCross.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public static void main(String[] args) {
    IPonte ponte = new Ponte(5);

    // Criação das threads representando pessoas
    Thread[] threads = new Thread[10];
    for (int i = 0; i < threads.length; i++) {
        final int threadId = i;
        threads[i] = new Thread(() -> {
            if (threadId % 2 == 0) {
                System.out.println("Pessoa " + threadId + " quer atravessar para a ilha.");
                ponte.inicioTravessiaIda();
                System.out.println("Pessoa " + threadId + " está atravessando para a ilha.");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ponte.fimTravessia();
                System.out.println("Pessoa " + threadId + " terminou a travessia para a ilha.");
            } else {
                System.out.println("Pessoa " + threadId + " quer atravessar de volta.");
                ponte.inicioTravessiaVolta();
                System.out.println("Pessoa " + threadId + " está atravessando de volta.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ponte.fimTravessia();
                System.out.println("Pessoa " + threadId + " terminou a travessia de volta.");
            }
        });
    }

    // Inicia as threads
    for (Thread thread : threads) {
        thread.start();
    }

    // Aguarda as threads terminarem
    for (Thread thread : threads) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
}
