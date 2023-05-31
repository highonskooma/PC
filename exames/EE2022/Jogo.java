package exames.EE2022;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

interface IPartida {
  boolean aposta(int n, int media) throws InterruptedException;
}

interface IJogo {
  Partida participa() throws InterruptedException;
}

class Partida implements IPartida {
  private int currentBets;
  private int[] bets;
  private Lock l;
  private Condition isFinished;
  private int maxPlayers;

  public Partida(int maxPlayers) {
    this.currentBets = 0;
    this.bets = new int[maxPlayers];
    this.l = new ReentrantLock();
    this.isFinished = l.newCondition();
    ;
    this.maxPlayers = maxPlayers;
  }

  public boolean aposta(int n, int media) throws InterruptedException {
    l.lock();
    try {
      currentBets++;

      // Se todas as apostas foram feitas, verifica se a média está correta
      if (currentBets == maxPlayers) {
        isFinished.signalAll();

        int soma = 0;
        for (int i = 0; i < maxPlayers; i++) {
          soma += bets[i];
        }
        int mediaCalculada = soma / maxPlayers;

        // Verifica se a média está correta e retorna o resultado
        return mediaCalculada == media;
      }

      while (currentBets < maxPlayers) {
        isFinished.await();
      }
      bets[currentBets-1] = n;

      return false;

    } finally {
      l.unlock();
    }
  }
}

public class Jogo implements IJogo {
  private List<Partida> games;
  private int playersWaiting;
  private int maxPlayers;
  private Lock l;
  private Condition canStart;

  public Jogo(int maxPlayers) {
    this.games = new ArrayList<>();
    this.maxPlayers = maxPlayers;
    this.playersWaiting = 0;
    this.l = new ReentrantLock();
    canStart = l.newCondition();
  }

  public Partida participa() throws InterruptedException {
    l.lock();

    playersWaiting++;

    if (playersWaiting == maxPlayers) {
      Partida p = new Partida(maxPlayers);
      games.add(p);
      playersWaiting = 0;
      canStart.signalAll();
      return p;
    }

    try {
      while (playersWaiting < maxPlayers) {
        canStart.await();
      }
    } finally {
      l.unlock();
    }

    return games.get(games.size() - 1);
  }
}
