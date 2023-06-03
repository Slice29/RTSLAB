
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Lab7App1 {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        Lock lockP9 = new ReentrantLock();
        Lock lockP10 = new ReentrantLock();
        new ExecutionThreadLeft(lockP9, lockP10, 4, 2, 6, cyclicBarrier).start();
        new ExecutionThreadRight(lockP9, lockP10, 5, 3, 7, cyclicBarrier).start();

    }
}

class ExecutionThreadRight extends Thread {
    CyclicBarrier cyclicBarrier;
    int activityDurMin;
    int activityDurMax;
    int transitionDuration;
    Lock p9;
    Lock p10;
    String name;
    Boolean p9Unlocked = false;
    Boolean p10Unlocked = false;

    public ExecutionThreadRight(Lock p9, Lock p10, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CyclicBarrier cyclicBarrier) {
        this.activityDurMin = activityMinDuration;
        this.activityDurMax = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.p9 = p9;
        this.p10 = p10;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");

            int k = (int) Math.round(Math.random() * (activityDurMax - activityDurMin) + activityDurMin);

            // we are forcing the thread that has the larger activity to "fall behind" to avoid deadlock
            try {
                Thread.sleep(k * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }

            while (!p10Unlocked) {
                if (p10.tryLock()) {
                    System.out.println(name + " - STATE 2");

                    int k1 = (int) Math.round(Math.random() * (activityDurMax - activityDurMin + 2) + activityDurMin + 2);

                    for (int i = 0; i < k1 * 10000; i++) {
                        i++;
                        i--;
                    }

                    while (!p9Unlocked) {
                        if (p9.tryLock()) {
                            System.out.println(name + " - STATE 3");
                            p9.unlock();
                            p9Unlocked = true;
                        }

                    }
                    p10.unlock();
                    p10Unlocked = true;
                }

            }


            try {
                Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // used to begin again to get the locks
            p10Unlocked = false;
            p9Unlocked = false;

            System.out.println(name + " - STATE 4");

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException es) {
                es.printStackTrace();
            }
        }
    }
}

class ExecutionThreadLeft extends Thread {
    CyclicBarrier cyclicBarrier;
    int activityMinDuration;
    int getActivityMinDuration;
    int transitionDuration;
    String name;
    Lock p9;
    Lock p10;
    Boolean p9Unlocked = false;
    Boolean p10Unlocked = false;

    public ExecutionThreadLeft(Lock p9, Lock p10, int transitionDuration, int activityMinDuration, int getActivityMinDuration, CyclicBarrier cyclicBarrier) {
        this.activityMinDuration = activityMinDuration;
        this.getActivityMinDuration = getActivityMinDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.p9 = p9;
        this.p10 = p10;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");
            int k = (int) Math.round(Math.random() * (getActivityMinDuration - activityMinDuration) + activityMinDuration);

            // we are forcing the thread that has the larger activity to "fall behind" to avoid deadlock
            try {
                Thread.sleep(k * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }

            while (!p9Unlocked) {
                if (p9.tryLock()) {
                    System.out.println(name + " - STATE 2");

                    int k1 = (int) Math.round(Math.random() * (getActivityMinDuration - activityMinDuration + 2) + activityMinDuration + 2);

                    for (int i = 0; i < k1 * 10000; i++) {
                        i++;
                        i--;
                    }

                    while (!p10Unlocked) {
                        if (p10.tryLock()) {
                            System.out.println(name + " - STATE 3");
                            p10.unlock();
                            p10Unlocked = true;
                        }

                    }
                    p9.unlock();
                    p9Unlocked = true;
                }


            }

            // used to begin again to get the locks
            p10Unlocked = false;
            p9Unlocked = false;

            try {
                Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 4");

            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException es) {
                es.printStackTrace();
            }
        }
    }


}



