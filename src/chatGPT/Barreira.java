package chatGPT;
/*Implemente em Java uma classe chamada Barreira, que representa uma barreira de sincronização para um grupo de threads. A barreira deve funcionar da seguinte forma:

    A classe Barreira possui um construtor que recebe como argumento o número total de threads que devem esperar na barreira.

    A classe Barreira possui um método chamado esperar() que as threads devem chamar quando desejarem esperar na barreira.

    Quando uma thread chama o método esperar(), ela deve bloquear até que todas as threads tenham chamado o método esperar().

    Assim que todas as threads tenham chamado o método esperar(), todas devem ser liberadas simultaneamente.

    Após a liberação das threads, a barreira é reiniciada para ser usada novamente.

Implemente a classe Barreira com base em monitores nativos do Java (synchronized, wait() e notifyAll()). 
Certifique-se de que a barreira funcione corretamente para qualquer número de threads especificado no construtor. */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Barreira {
  private int maxThreads;
  private int currentThreads;
  private Lock lock;
  private Condition isFull;

  public Barreira(int maxThreads) {
    this.maxThreads = maxThreads;
    this.currentThreads = 0;
    this.lock = new ReentrantLock();
    this.isFull = lock.newCondition();
  }

  public void esperar() throws InterruptedException {
    lock.lock();
    try {
      currentThreads++;
      if (currentThreads < maxThreads) {
        isFull.await();
      } else {
        isFull.signalAll();
        currentThreads = 0;
      }
    } finally {
      lock.unlock();
    }
  }
}

