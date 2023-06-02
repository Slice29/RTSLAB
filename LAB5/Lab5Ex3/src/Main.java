import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Producer extends Thread{
    Lock l;
    ArrayList numbers ;
    int nr;
    public Producer(Lock l,int nr){
        this.l=l;
        this.nr=nr;
        numbers = new ArrayList();
    }

    public void run(){
        while (true) {
            this.l.lock();
            Random random = new Random();
            for (int i = 0; i < nr; i++) {
                numbers.clear();
                numbers.add(random.nextInt(20));
            }
            this.l.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public ArrayList getNumbers() {
        return numbers;
    }
}

class Consumer extends Thread{
    Lock l;
    Producer t;
    public Consumer(Lock l, Producer t){
        this.l=l;
        this.t=t;
    }

    public void run() {
        while (true) {
            this.l.lock();
            System.out.println(this.getName() + " acquired: " + t.getNumbers().toString());
            this.l.unlock();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}

public class Main {
    public static void main(String args[]){
        Lock l = new ReentrantLock();
        Producer p = new Producer(l,5);
        Consumer c1,c2,c3;
        c1=new Consumer(l,p);
        c2=new Consumer(l,p);
        c3=new Consumer(l,p);
        p.start();
        c1.start();
        c2.start();
        c3.start();
    }
}