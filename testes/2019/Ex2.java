import java.util.Random;

interface Jogo {
  Partida participa() throws InterruptedException;
}

interface Partida {
  String adivinha(int n);
}

class JogoImpl implements Jogo {
  PartidaImpl p = new PartidaImpl();
  int jogadores = 0;

  public synchronized Partida participa() throws InterruptedException {
    PartidaImpl ps = p;
    jogadores++;
    //System.out.println("jogadores: " + jogadores);
    if (jogadores == 4) {
      notifyAll();
      jogadores = 0;
      ps.start();
      p = new PartidaImpl();
    } else {
      while (p == ps)
        wait();
    }

    return ps;
  }
}

class PartidaImpl implements Partida {
  int tentativas = 0;
  boolean ganhou = false;
  boolean timeout = false;
  int numero;

  public void start() throws InterruptedException {
    numero = new Random().nextInt(100);
    System.out.println("numero: " + numero);
    new Thread(() -> {
      try {
        Thread.sleep(60000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      timeout();
    }).start();
  }

  synchronized void timeout() {
    timeout = true;
  }

  public synchronized String adivinha(int n) {
    if (ganhou)
      return "PERDEU";
    else if (tentativas >= 100)
      return "TENTATIVAS";
    else if (timeout)
      return "TEMPO";
    else {
      tentativas++;
      if (n > numero)
        return "MENOR";
      else if (n < numero)
        return "MAIOR";
      else {
        ganhou = true;
        return "GANHOU";
      }
    }
  }
}

public class Ex2 {
  public static void main(String[] args) throws InterruptedException {
    JogoImpl j = new JogoImpl();
    int i = 0;
    Thread[] threads = new Thread[4];
    for (i=0; i < 4; i++) {
      Thread.sleep(1000);
      System.out.println("new thread: " + i);
      threads[i] = new Thread(() -> {
        try {
          j.participa();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      });
      threads[i].start();
    }
  }
}
