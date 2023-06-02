// App 1 Implementation
class Thread1 extends Thread{
    Integer monitor;
    Thread1(Integer monitor)
    {
        this.monitor = monitor;
    }
    public void run()
    {
        System.out.println("Thread 1 started. Waiting for monitor...");
        synchronized (monitor){
            System.out.println("Thread 1 acquired monitor. Doing activity...");
            Main.doActivity(2,4);
            System.out.println("Thread 1 did activity. Going to sleep...");
            try {
                this.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 woke up. Releasing resource. Execution done.");
        }
    }
}

class Thread2 extends Thread{
    Integer monitor1;
    Integer monitor2;
    Thread2(Integer monitor1, Integer monitor2)
    {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }
    public void run()
    {
        System.out.println("Thread 2 started execution. Waiting for monitors...");
        synchronized (monitor1)
        {
            synchronized (monitor2)
            {
                System.out.println("Thread 2 acquired monitors. Doing activity...");
                Main.doActivity(3,6);
                System.out.println("Thread 2 did activity. Going to sleep...");
                try {
                    this.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Thread 2 woke up. Releasing monitor. Execution done.");

    }
}

class Thread3 extends Thread{
    Integer monitor;

    Thread3(Integer monitor)
    {
        this.monitor = monitor;
    }
    public void run(){
        System.out.println("Thread 3 started. Waiting for monitor.");
        synchronized(monitor)
        {
            System.out.println("Thread 3 acquired monitor. Doing activity...");
            Main.doActivity(2,5);
            System.out.println("Thread 3 did activity. Going to sleep...");
            try {
                this.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 3 woke up. Releasing monitor. Execution done.");

        }
    }

}


public class Main {
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
        Integer monitor1 = new Integer(0);
        Integer monitor2 = new Integer(0);
        Thread1 th1 = new Thread1(monitor1);
        Thread2 th2 = new Thread2(monitor1,monitor2);
        Thread3 th3 = new Thread3(monitor2);
        th1.start();
        th2.start();
        th3.start();
    }
}
