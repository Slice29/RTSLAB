import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

class SideThreads extends Thread{
    Semaphore sem;
    CountDownLatch latch;
    int t1,t2,s;
    SideThreads(Semaphore sem, CountDownLatch latch, int t1, int t2, int s)
    {
        this.sem= sem;
        this.latch = latch;
        this.t1 = t1;
        this.t2 = t2;
        this.s = s;
    }
    public void run()
    {
        System.out.println(this.getName() + " started. Waiting for resource...");
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " acquired resource. Doing activity...");
        App2WithSemaphore.doActivity(t1,t2);
        System.out.println(this.getName() + " going to sleep");
        try {
            this.sleep(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sem.release();
        System.out.println(this.getName() + " releasing resource. Waiting...");
        latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MiddleThread extends Thread{
    Semaphore sem1;
    Semaphore sem2;
    CountDownLatch latch;
    MiddleThread(Semaphore sem1, Semaphore sem2, CountDownLatch latch)
    {
        this.sem1 = sem1;
        this.sem2 = sem2;
        this.latch = latch;
    }
    public void run(){
        System.out.println("Middle thread started");
        try {
            sem1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            sem2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Middle thread acquired locks.Doing activity... ");
        App2WithSemaphore.doActivity(3,6);
        try {
            this.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sem1.release();
        sem2.release();
        System.out.println("Middle thread done. Waiting at bar...");
       latch.countDown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class App2WithSemaphore {
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
            CountDownLatch latch = new CountDownLatch(4);
            MiddleThread midTh = new MiddleThread(sem1,sem2,latch);
            SideThreads th1 = new SideThreads(sem1,latch,2,4,4);
            SideThreads th2 = new SideThreads(sem2,latch,2,5,5);
            midTh.start();
            th1.start();
            th2.start();
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("In main. Resetting latch.");

        }
    }
}
