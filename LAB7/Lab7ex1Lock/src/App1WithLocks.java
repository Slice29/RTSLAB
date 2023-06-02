import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

class Thread1 extends Thread{
    ReentrantLock lock1,lock2;
    int t11,t12,s;
    int t21,t22;
    CyclicBarrier bar;
    Thread1(ReentrantLock lock1,ReentrantLock lock2,CyclicBarrier bar, int t11,int t12,int t21, int t22,int s)
    {
        this.lock1 = lock1;
        this.lock2 = lock2;
        this.bar = bar;
        this.t11 = t11;
        this.t12 = t12;
        this.t21 = t21;
        this.t22 = t22;
        this.s = s;
    }
    public void run()
    {
        System.out.println(this.getName() + " started. Doing activity...");
        App1WithLocks.doActivity(t11,t12);
        System.out.println(this.getName() + " did activity. Waiting for lock...");
        lock1.lock();
        System.out.println(this.getName() + " acquired lock. Doing second activity");
        App1WithLocks.doActivity(t21,t22);
        System.out.println(this.getName() + " did activity. Waiting for P10..." );
        lock2.lock();
        System.out.println(this.getName() + "acquired both locks. Sleeping...");
        try {
            this.sleep(s * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.getName() + " woke up. Unlocking locks. Waiting at barrier...");
        lock1.unlock();
        lock2.unlock();
        try {
            bar.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

public class App1WithLocks {
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
        CyclicBarrier bar = new CyclicBarrier(3);


        while(true)
        {

            Thread1 th1 = new Thread1(lock1,lock2,bar,2,4,4,6,4);
            Thread1 th2 = new Thread1(lock1,lock2,bar,3,5,5,7,5);
            th1.start();
            th2.start();
            try {
                bar.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("In main. Restarting execution. Resetting barrier...");
            bar.reset();
        }
    }
}
