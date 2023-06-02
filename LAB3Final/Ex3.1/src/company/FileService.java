package company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class FileService {
    private String fileName;
    private BufferedReader in;
    private PrintWriter out;
    private final Object lock = new Object();

    public FileService(String fname) {
        this.fileName = fname;
        try {
            out = new PrintWriter(new FileWriter(fileName, true));
            in = new BufferedReader(new FileReader(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String msg) {
        Date date = new Date(System.currentTimeMillis());
        synchronized (lock) {
            out.println("Date: " + date);
            out.println("Message: " + msg);
            out.flush();
        }
    }

    public String read() throws IOException {
        String iterator;
        String last = "no message to read";
        synchronized (lock) {
            while ((iterator = in.readLine()) != null) {
                last = new Date(System.currentTimeMillis()) + " - " + iterator;
            }
        }
        return last;
    }
}
