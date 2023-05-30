package guiao5;

class BoundedBuffer<T> {
  T buffer[];
  int iput = 0, iget = 0;
  private int nelems = 0;

  BoundedBuffer (int N) {
    buffer = (T[]) new Object[N];
  }

  public synchronized T get() throws InterruptedException { 
    while(nelems == 0) wait();
    T res = buffer[iget];
    nelems--;
    iget = (iget+1) % buffer.length;
    notifyAll();
    return res;
  }

  public synchronized void put(T x) throws InterruptedException {
    while(!(nelems < buffer.length)) wait();
    buffer[iput] = x;
    iput = (iput+1) % buffer.length;
    nelems++;
    notifyAll();
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
          } catch (Exception e) {}
      });

      Thread consumidor = new Thread(() -> {
          try {
              while (true) {
                  System.out.println("Getting");
                  System.out.println("x = " + buffer.get());
                  Thread.sleep(2000);
              }
          } catch (Exception e) {}
      });

      consumidor.start();
      produtor.start();
  }
}