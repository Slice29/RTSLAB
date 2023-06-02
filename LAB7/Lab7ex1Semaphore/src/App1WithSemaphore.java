import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

class Thread1 extends Thread{
    Semaphore sem1,sem2;
    int t11,t12,s;
    int t21,t22;
    CountDownLatch latch;
    Thread1(Semaphore sem1, Semaphore sem2,CountDownLatch latch, int t11,int t12,int t21, int t22,int s)
    {
        this.sem1 = sem1;
        this.sem2 = sem2;
        this.latch = latch;
        this.t11 = t11;
        this.t12 = t12;
        this.t21 = t21;
        this.t22 = t22;
        this.s = s;
    }
    public void run()
    {
        System.out.println(this.getName() + " started. Doing activity...");
        App1WithSemaphore.doActivity(t11,t12);
        System.out.println(this.getName() + " did activity. Waiting for semaphore...");
        try {
            sem1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " acquired semaphore. Doing second activity");
        App1WithSemaphore.doActivity(t21,t22);
        System.out.println(this.getName() + " did activity. Waiting for P10..." );
        try {
            sem2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + "acquired both Semaphores. Sleeping...");
        try {
            this.sleep(s * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " woke up. Releasing semaphores. Waiting at latch...");
        sem1.release();
        sem2.release();

       latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class App1WithSemaphore {
    public static int x;

    public static void doActivity(int min, int max)
    {
        int secs = (int)Math.floor(Math.random()*(max-min) + min);
        for(long i =0; i < secs * 10000000l;i++)
        {
            x++;
            x--;
        }
    }

    public static void main(String[] args) {
       Semaphore sem1 = new Semaphore(1);
       Semaphore sem2 = new Semaphore(1);



        while(true)
        {
            CountDownLatch latch = new CountDownLatch(3);
            Thread1 th1 = new Thread1(sem1,sem2,latch,2,4,4,6,4);
            Thread1 th2 = new Thread1(sem1,sem2,latch,3,5,5,7,5);
            th1.start();
            th2.start();
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("In main at latch. Restarting...");
        }
    }
}
