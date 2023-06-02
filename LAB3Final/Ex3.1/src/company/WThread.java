package company;

public class WThread extends  Thread {
    FileService fileService;
    public  WThread(FileService service){
        this.fileService = service;
    }
    public void run(){
        synchronized (this){
            while (!Main.isStopThreads()) {
                String msg = String.valueOf(Math.round(Math.random() * 100));
                fileService.write(msg);
                System.out.println("I wrote in the file " + msg);
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


