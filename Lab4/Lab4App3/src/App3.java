class ExecutionThread extends Thread{
    Integer monitor;
    int sleepvalue, activity_max,activity_min;

    public ExecutionThread(Integer monitor, int sleepvalue,int activity_min, int activity_max){
        this.monitor=monitor;
        this.sleepvalue=sleepvalue;
        this.activity_min=activity_min;
        this.activity_max=activity_max;
    }


    public void run() {
        while(true) {
            System.out.println(this.getName() + " - STATE 1");
            synchronized (monitor) {
                System.out.println(this.getName() + " - STATE 2");
                int k = (int) Math.round(Math.random() * (activity_max - activity_min) + activity_min);
                for (int i = 0; i < k * 100000; i++) {
                    i++;
                    i--;
                }
            }
            System.out.println(this.getName() + " - STATE 3");
            try {
                Thread.sleep(Math.round(Math.random() * sleepvalue * 500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this.getName() + " - STATE 4");

        }
    }
}


public class App3 {
    public static void main(String[] args){
        Integer monitor = new Integer(1);
        new ExecutionThread(monitor,5,3,6).start();
        new ExecutionThread(monitor,6,5,7).start();
        new ExecutionThread(monitor,3,4,7).start();
    }
}