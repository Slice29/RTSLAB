
import java.util.concurrent.Semaphore;

class Thread1 extends Thread {
    int name, delay, k, permit;
    Semaphore sem;

    Thread1(int n, Semaphore sem, int delay, int k, int permit) {
        this.name = n;
        this.sem = sem;
        this.delay = delay;
        this.k = k;
        this.permit = permit;
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Thread " + name +" started");
                Thread.sleep(this.delay * 500);
                System.out.println("Thread " + name +" woke up");
                this.sem.acquire(this.permit);
                System.out.println("Thread " + name + " acquired a permit. Doing activity...");
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
                this.sem.release();
                System.out.println("Thread " + name + " released a permit");
                System.out.println("Thread " + name +" State 4");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String args[]) {
        Semaphore s = new Semaphore(4);
        Thread1 th1, th2;
        th1 = new Thread1(1, s, 2, (int) Math.round(Math.random() * 3 + 2), 2);
        th2 = new Thread1(2, s, 4, (int) Math.round(Math.random() * 3 + 3), 1);
        th1.start();
        th2.start();
    }
}
