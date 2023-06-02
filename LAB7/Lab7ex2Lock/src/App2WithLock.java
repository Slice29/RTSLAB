import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

class SideThreads extends Thread{
    ReentrantLock lock;
    CyclicBarrier bar;
    int t1,t2,s;
    SideThreads(ReentrantLock lock,CyclicBarrier bar,int t1,int t2,int s)
    {
        this.lock = lock;
        this.bar = bar;
        this.t1 = t1;
        this.t2 = t2;
        this.s = s;
    }
    public void run()
    {
        System.out.println(this.getName() + " started. Waiting for resource...");
        lock.lock();
        System.out.println(this.getName() + " acquired resource. Doing activity...");
        App2WithLock.doActivity(t1,t2);
        System.out.println(this.getName() + " going to sleep");
        try {
            this.sleep(s*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println(this.getName() + " releasing resource. Waiting...");
        try {
            bar.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class MiddleThread extends Thread{
    ReentrantLock lock1;
    ReentrantLock lock2;
    CyclicBarrier bar;
    MiddleThread(ReentrantLock lock1, ReentrantLock lock2, CyclicBarrier bar)
    {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.bar = bar;
    }
    public void run(){
        System.out.println("Middle thread started");
        lock1.lock();
        lock2.lock();
        System.out.println("Middle thread acquired locks.Doing activity... ");
        App2WithLock.doActivity(3,6);
        try {
            this.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock1.unlock();
        lock2.unlock();
        System.out.println("Middle thread done. Waiting at bar...");
        try {
            bar.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}


public class App2WithLock {
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
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();
        CyclicBarrier bar = new CyclicBarrier(4);
        while(true)
        {
            MiddleThread midTh = new MiddleThread(lock1,lock2,bar);
            SideThreads th1 = new SideThreads(lock1,bar,2,4,4);
            SideThreads th2 = new SideThreads(lock2,bar,2,5,5);
            midTh.start();
            th1.start();
            th2.start();
            try {
                bar.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

            System.out.println("In main. Resetting barrier...");
            bar.reset();
        }
    }
}
