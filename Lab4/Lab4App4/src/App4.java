class Thread1 extends Thread{
    Integer monitor1, monitor2;
    Thread1(Integer monitor1, Integer monitor2)
    {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }
    public void run(){
                System.out.println(this.getName() + " started. Going to sleep...");
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName() + " woke up. Doing activity");
                App4.doActivity(2,3);
                System.out.println(this.getName() + " did activity. Releasing monitors. Execution done");
        synchronized (monitor1)
        {
            monitor1.notify();
        }

        synchronized (monitor2)
        {
            monitor2.notify();
        }

    }
}

class OtherThreads extends Thread {
    Integer monitor;
    int t1, t2;

    OtherThreads(Integer monitor, int t1, int t2) {
        this.monitor = monitor;
        this.t1 = t1;
        this.t2 = t2;
    }

    public void run() {
        System.out.println(this.getName() + " started. Waiting for monitors.");
        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + " acquired monitor. Doing activity...");
            App4.doActivity(t1, t2);
            System.out.println(this.getName() + " did activity. Execution done.");
        }


    }
}


public class App4 {

    public static int x;
    public static void doActivity(int min, int max){

        int secs = (int)Math.floor(Math.random()*(max-min) + min);
        for(long i = 1; i <= secs * 10000000l;i++)
        {
            x++;
            x--;
        }
    }

    public static void main(String[] args) {
        Integer monitor1 = new Integer(1);
        Integer monitor2 = new Integer(1);
        Thread1 th1 = new Thread1(monitor1,monitor2);
        OtherThreads th2 = new OtherThreads(monitor1,3,5);
        OtherThreads th3 = new OtherThreads(monitor2,4,6);
        th1.start();
        th2.start();
        th3.start();

        // Yes the join mechanism works because it waits for the threads to execute before resuming main execution
        try {
            th1.join();
            System.out.println("Thread 1 joined");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            th2.join();
            System.out.println("Thread 2 joined");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            th3.join();
            System.out.println("Thread 3 joined");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("In main execution done.");
    }
}

