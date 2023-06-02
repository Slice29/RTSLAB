import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore semph = new Semaphore(2, true);
        new ExeThread(semph, 3, 4, 7).start();
        new ExeThread(semph, 6, 5, 7).start();
        new ExeThread(semph, 5, 3, 6).start();
    }
}

class ExeThread extends Thread {
    Semaphore semph;
    int sleepMax, activityMin, activityMax;
    String name;

    public ExeThread(Semaphore semph, int sleepMax, int activityMin, int activityMax) {
        this.semph = semph;
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
                semph.acquire();
                System.out.println(name + " - STATE 2");
                int k1 = (int) Math.round(Math.random() * (activityMax - activityMin) + activityMin);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                semph.release();
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