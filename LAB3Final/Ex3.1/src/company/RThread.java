package company;

public class RThread extends Thread {

    FileService service;

    public RThread(FileService service) {
        this.service = service;
    }

    public void run() {
        synchronized (this) {
            while (!Main.isStopThreads()) {
                try {
                    String readMsg = service.read();
                    System.out.println(readMsg);
                    System.out.println("I read " + readMsg);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
