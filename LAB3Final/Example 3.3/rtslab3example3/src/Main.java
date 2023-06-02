

// Defining exception to enforce the thread call to be at least as it's specified in the requirements
class CustomException extends Exception {
    private String errorMessage;
    public  CustomException(String message){
        this.errorMessage = message;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}



class JoinTestThread extends Thread{
    // variable to check if both the threads were called
    private static int counter = 0;

    // changing the type of t from Thread to our specific type of thread to call the getN method
    JoinTestThread t;

    // n is the number we compute the divisor sum of
    int n;

    // getter method to check the joined thread's passed N number
    public int getN(){
        return this.n;
    }

    // counter getter
    public int getCounter()
    {
        return counter;
    }
    int threadDivCount = 0;
    JoinTestThread(String name, JoinTestThread t, int n){
        this.setName(name);
        this.t=t;
        this.n = n;
    }
    public void run() {
        System.out.println("Thread "+this.getName()+" has entered the run() method");

        // enforcing the numbers to be bigger than the ones specified in the requirement
        // without changing the code in the main method, we need to check the w2 thread first, then the w1 thread,
        try {
            if(this.n < 20000)
            {
                throw new CustomException("Invalid thread call");
            }
            else
            {
                for(int i = 1; i <= Math.sqrt(this.n);i++)
                {
                    if(this.n%i == 0)
                    {
                        this.threadDivCount += i;
                    }
                }
            }
            try{
                // the threads are called only once, so it's okay if we hard code to compare the first thread n to 20k
                // and the second thread to 50k
                if (t!= null)
                if (t.getN() >= 50000) t.join();
                else
                    throw new CustomException("Invalid Thread call");
            }
            catch (CustomException e)
            {
                e.getErrorMessage();
            }

            System.out.println("Thread "+this.getName()+" adding the computed divisors to the static variable");
            Main.divisorSum += this.threadDivCount;
            counter++;
            System.out.println("Thread "+this.getName()+" has terminated operation.");
        }
        catch(Exception e){e.printStackTrace();}
        }
    }
public class Main {

    public static int divisorSum = 0;
    public static void main(String[] args){

        JoinTestThread w1 = new JoinTestThread("Thread 1",null,100000);
        JoinTestThread w2 = new JoinTestThread("Thread 2",w1,800000);
        w1.start();
        w2.start();
        // waiting for the joins to make sure the divisor count is defined correctly
        try {
            w1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            w2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // checking if the divisors were computed right
        if(w2.getCounter() == 2)
        System.out.println("The divisor count is " + divisorSum);
        else System.out.println("Error computing the divisors");
    }
}
