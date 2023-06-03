import java.util.concurrent.CountDownLatch;

public class Lab7App3 {

    static Boolean cond10 = false;
    static Boolean cond6 = false;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        // x = y = 5
        int x = 10;
        int y = 10;

        Integer monitorP10 = 10;
        Integer monitorP6 = 6;

        new ExecutionThreadLeft(monitorP10, monitorP6, 7, 2, 3, countDownLatch).start();
        new ExecutionThreadMiddle(monitorP6, x, 3, 5, countDownLatch).start();
        new ExecutionThreadRight(monitorP10, y, 4, 6, countDownLatch).start();
    }
}


class ExecutionThreadRight extends Thread {
    CountDownLatch countDownLatch;
    int activityDurationMin;
    int activityDurationMax;
    int transitionDuration;

    String name;
    Integer lockp10;

    public ExecutionThreadRight(Integer monitorP10, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityDurationMin = activityMinDuration;
        this.activityDurationMax = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.lockp10 = monitorP10;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        System.out.println(name + " - STATE 1");

        try {
            Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        synchronized (lockp10) {
            while (!Lab7App3.cond6) {
                try {
                    lockp10.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(name + " - STATE 2");
            int k = (int) Math.round(Math.random() * (activityDurationMax - activityDurationMin) + activityDurationMin);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
            }


            System.out.println(name + " - STATE 3");
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException es) {
                es.printStackTrace();
            }
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

class ExecutionThreadLeft extends Thread {
    CountDownLatch countDownLatch;
    int activityDurationMin;
    int activityDurationMax;
    int transitionDuration;
    String name;

    Integer lockp10;
    Integer lockp6;


    public ExecutionThreadLeft(Integer monitorP10, Integer monitorP6, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityDurationMin = activityMinDuration;
        this.activityDurationMax = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.lockp10 = monitorP10;
        this.lockp6 = monitorP6;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        System.out.println(name + " - STATE 1");

        try {
            Thread.sleep(Math.round(Math.random() * (transitionDuration) * 500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + " - STATE 2");
        int k = (int) Math.round(Math.random() * (activityDurationMax - activityDurationMin) + activityDurationMin);
        for (int i = 0; i < k * 100000; i++) {
            i++;
            i--;
        }

        synchronized (lockp6) {
            Lab7App3.cond6 = true;
            lockp6.notify();
        }

        synchronized (lockp10) {
            Lab7App3.cond10 = true;
            lockp10.notify();
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

class ExecutionThreadMiddle extends Thread {
    CountDownLatch countDownLatch;
    int activityDurationMin;
    int activityDurationMax;
    int transitionDuration;
    String name;

    Integer lockp6;


    public ExecutionThreadMiddle(Integer monitorP6, int transitionDuration, int activityMinDuration, int actvityMaxDuration, CountDownLatch countDownLatch) {
        this.activityDurationMin = activityMinDuration;
        this.activityDurationMax = actvityMaxDuration;
        this.transitionDuration = transitionDuration;
        this.name = getName();
        this.lockp6 = monitorP6;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

        System.out.println(name + " - STATE 1");

        synchronized (lockp6) {
            while (!Lab7App3.cond6) {
                try {
                    lockp6.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(name + " - STATE 2");
            int k = (int) Math.round(Math.random() * (activityDurationMax - activityDurationMin) + activityDurationMin);
            for (int i = 0; i < k * 100000; i++) {
                i++;
                i--;
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



