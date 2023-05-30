interface Controller {
  int request_resource(int i);
  void release_resource(int i);
}

public class ResourceController implements Controller {
  private int maxThreads; // nr max de threads q podem utilizar um recurso
  private int[] resourceAccessCounts; //nr atual de threads q estão a utilizar cada recurso
  private boolean[] resourceAvailable; //condição de bloqueio de cada recurso

  public ResourceController (int maxThreads) {
    this.maxThreads = maxThreads;
    this.resourceAccessCounts = new int[2];
    this.resourceAvailable = new boolean[2];
    this.resourceAvailable[0] = true;
    this.resourceAvailable[1] = true;
  }

  public int request_resource(int i) {
      synchronized (this) {
          while (!resourceAvailable[i] || resourceAccessCounts[i] >= maxThreads) {
              try {
                  wait();
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                  return -1;
              }
          }
          resourceAccessCounts[i]++;
          resourceAvailable[i] = false;
      }
      return i;
  }

  public void release_resource(int i) {
      synchronized (this) {
          resourceAccessCounts[i]--;
          if (resourceAccessCounts[i] == 0) {
              resourceAvailable[i] = true;
          }
          notifyAll();
      }
  }

  public static void main(String[] args) {
    final int MAX_THREADS = 3;
    final int NUM_RESOURCES = 2;

    Controller controller = new ResourceController(MAX_THREADS);

    // Create and start multiple threads to access the resources
    for (int i = 0; i < MAX_THREADS; i++) {
        int threadId = i;
        Thread thread = new Thread(() -> {
            // Thread requests and accesses resource 0
            int resource0 = controller.request_resource(0);
            System.out.println("Thread " + threadId + " obtained resource 0");

            // Perform some work with resource 0
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Release resource 0
            controller.release_resource(resource0);
            System.out.println("Thread " + threadId + " released resource 0");

            // Thread requests and accesses resource 1
            int resource1 = controller.request_resource(1);
            System.out.println("Thread " + threadId + " obtained resource 1");

            // Perform some work with resource 1
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Release resource 1
            controller.release_resource(resource1);
            System.out.println("Thread " + threadId + " released resource 1");
        });
        thread.start();
    }
  }
}


