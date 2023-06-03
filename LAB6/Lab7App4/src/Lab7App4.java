
import java.util.concurrent.Semaphore;

public class Lab7App4 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2, true);
        new ExecutionThread(semaphore, 3, 4, 7).start();
        new ExecutionThread(semaphore, 6, 5, 7).start();
        new ExecutionThread(semaphore, 5, 3, 6).start();
    }
}

class ExecutionThread extends Thread {
    Semaphore semaphore;
    int sleepMax, activityMin, activityMax;
    String name;

    public ExecutionThread(Semaphore semaphore, int sleepMax, int activityMin, int activityMax) {
        this.semaphore = semaphore;
        this.sleepMax = sleepMax;
        this.activityMax = activityMax;
        this.activityMin = activityMin;
        this.name = this.getName();
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(name + " - STATE 1");

            try {
                semaphore.acquire();
                System.out.println(name + " - STATE 2");
                int k1 = (int) Math.round(Math.random() * (activityMax - activityMin) + activityMin);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 3");

            try {
                Thread.sleep(Math.round(Math.random() * (sleepMax) * 500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " - STATE 4");
        }
    }
}