
class Thread1 extends Thread{
    Integer monitor1, monitor2;

    Thread1(Integer monitor1, Integer monitor2)
    {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }
    public void run()
    {
        System.out.println("Thread 1 started. Doing activity...");
        App2.doActivity(2,4);
        System.out.println("Thread 1 did first activity. Waiting for monitors...");
        synchronized (monitor1)
        {
            synchronized (monitor2)
            {
                System.out.println("Thread 1 acquired monitors. Doing activity...");
                App2.doActivity(4,6);
                System.out.println("Thread 1 did activity. Going to sleep...");
                try {
                    this.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 woke up. Execution done");
            }
        }
    }
}

class Thread2 extends Thread{
    Integer monitor1, monitor2;

    Thread2(Integer monitor1, Integer monitor2)
    {
        this.monitor1 = monitor1;
        this.monitor2 = monitor2;
    }
    public void run()
    {
        System.out.println("Thread 2 started. Doing activity...");
        App2.doActivity(3,5);
        System.out.println("Thread 2 did first activity. Waiting for monitors...");
        synchronized (monitor1)
        {
            synchronized (monitor2)
            {
                System.out.println("Thread 2 acquired monitors. Doing activity...");
                App2.doActivity(5,7);
                System.out.println("Thread 2 did activity. Going to sleep...");
                try {
                    this.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 woke up. Execution done");
            }
        }
    }
}




public class App2 {

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
        Thread1 th1 = new Thread1(monitor1,monitor2);
        Thread2 th2 = new Thread2(monitor1,monitor2);
        th1.start();
        th2.start();
    }
}
