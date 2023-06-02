import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Lock lockP9 = new ReentrantLock();
        Lock lockP10 = new ReentrantLock();

        new ExeThreadLeft(lockP9, lockP10, 4, 2, 6).start();
        new ExeThreadRight(lockP9, lockP10, 5, 3, 7).start();


    }
}


class ExeThreadLeft extends Thread {

    int activityMinDuration;
    int activityMaxDuration;
    int transitionDuration;
    String name;
    Lock p9;
    Lock p10;
    Boolean p9Unlocked = false;
    Boolean p10Unlocked = false;

    public ExeThreadLeft(Lock p9, Lock p10, int transitionDuration, int activityMinDuration, int activityMaxDuration) {
        this.activityMinDuration = activityMinDuration;
        this.activityMaxDuration = activityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.p9 = p9;
        this.p10 = p10;
    }

    @Override
    public void run() {
        System.out.println(name + " - STATE 1");
        int k = (int) Math.round(Math.random() * (activityMaxDuration - activityMinDuration) + activityMinDuration);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }

        while (!p9Unlocked) {
            if (p9.tryLock()) {
                System.out.println(name + " - STATE 2");

                int k1 = (int) Math.round(Math.random() * (activityMaxDuration - activityMinDuration + 2) + activityMinDuration + 2);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                p9.unlock();
                p9Unlocked = true;
            }

        }

        while (!p10Unlocked) {
            if (p10.tryLock()) {
                System.out.println(name + " - STATE 3");
                p10.unlock();
                p10Unlocked = true;
            }

        }

        try {
            Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " - STATE 4");
    }


}


class ExeThreadRight extends Thread {

    int activityMinDuration;
    int actvityMaxDuration;
    int transitionDuration;
    String name;
    Lock p9;
    Lock p10;
    Boolean p9Unlocked = false;
    Boolean p10Unlocked = false;

    public ExeThreadRight(Lock p9, Lock p10, int transitionDuration, int activityMinDuration, int actvityMaxDuration) {
        this.activityMinDuration = activityMinDuration;
        this.actvityMaxDuration = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.p9 = p9;
        this.p10 = p10;
    }

    @Override
    public void run() {
        System.out.println(name + " - STATE 1");
        int k = (int) Math.round(Math.random() * (actvityMaxDuration - activityMinDuration) + activityMinDuration);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }

        while (!p10Unlocked) {
            if (p10.tryLock()) {
                System.out.println(name + " - STATE 2");

                int k1 = (int) Math.round(Math.random() * (actvityMaxDuration - activityMinDuration + 2) + activityMinDuration + 2);
                for (int i = 0; i < k1 * 100000; i++) {
                    i++;
                    i--;
                }
                p10.unlock();
                p10Unlocked = true;
            }

        }

        while (!p9Unlocked) {
            if (p9.tryLock()) {
                System.out.println(name + " - STATE 3");
                p9.unlock();
                p9Unlocked = true;
            }

        }

        try {
            Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " - STATE 4");
    }


}
