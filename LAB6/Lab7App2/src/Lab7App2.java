
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Lab7App2 {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Semaphore semaphore9 = new Semaphore(1);
        Semaphore semaphore10 = new Semaphore(1);

        new ExecutionThreadLeft(semaphore9, 1, 4, 2, 6, countDownLatch).start();
        new ExecutionThreadRight(semaphore10, 1, 5, 3, 6, countDownLatch).start();
        new ExecutionThreadMiddle(semaphore9, semaphore10, 1, 3, 2, 5, countDownLatch).start();

    }
}

class ExecutionThreadRight extends Thread {
    CountDownLatch countDownLatch;

    int activityMinDuration;
    int actvityMaxDuration;
    int transitionDuration;

    Semaphore s10;
    int permise;

    String name;

    public ExecutionThreadRight(Semaphore s10, int permise, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityMinDuration = activityMinDuration;
        this.actvityMaxDuration = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.s10 = s10;
        this.permise = permise;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");

            try {
                s10.acquire(permise);
                System.out.println(name + " - STATE 2");
                int k1 = (int) Math.round(Math.random() * (actvityMaxDuration - activityMinDuration) + activityMinDuration);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                s10.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 3");
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException es) {
                es.printStackTrace();
            }

        }
    }
}

class ExecutionThreadLeft extends Thread {
    CountDownLatch countDownLatch;

    int activityMinDuration;
    int actvityMaxDuration;
    int transitionDuration;

    Semaphore s9;
    int permise;

    String name;

    public ExecutionThreadLeft(Semaphore s9, int permise, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityMinDuration = activityMinDuration;
        this.actvityMaxDuration = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.s9 = s9;
        this.permise = permise;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");

            try {
                s9.acquire(permise);
                System.out.println(name + " - STATE 2");
                int k1 = (int) Math.round(Math.random() * (actvityMaxDuration - activityMinDuration) + activityMinDuration);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                s9.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 3");
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException es) {
                es.printStackTrace();
            }

        }
    }


}

class ExecutionThreadMiddle extends Thread {
    CountDownLatch countDownLatch;

    int activityMinDuration;
    int actvityMaxDuration;
    int transitionDuration;

    Semaphore s9;
    Semaphore s10;
    int permise;

    String name;

    public ExecutionThreadMiddle(Semaphore s9, Semaphore s10, int permise, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityMinDuration = activityMinDuration;
        this.actvityMaxDuration = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.s9 = s9;
        this.s10 = s10;

        this.permise = permise;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");

            try {
                s9.acquire(1);
                s10.acquire(1);
                System.out.println(name + " - STATE 2");
                int k1 = (int) Math.round(Math.random() * (actvityMaxDuration - activityMinDuration) + activityMinDuration);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                s9.release();
                s10.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 3");
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException es) {
                es.printStackTrace();
            }

        }
    }


}





