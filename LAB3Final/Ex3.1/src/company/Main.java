package company;

public class Main {
    private static boolean stopThreads = false;

    public static void main(String[] args) {
        FileService fileService = new FileService("messages.txt");
        RThread reader = new RThread(fileService);
        WThread writer = new WThread(fileService);
        reader.start();
        writer.start();
    }

    public static boolean isStopThreads(){
        return stopThreads;
    }
}
