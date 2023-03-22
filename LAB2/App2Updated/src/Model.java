import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Model {
    private ExecutorService executorService;
    private int[] priorities = {Thread.MIN_PRIORITY, Thread.NORM_PRIORITY, Thread.NORM_PRIORITY, Thread.MAX_PRIORITY, Thread.MAX_PRIORITY, Thread.MAX_PRIORITY};
    private View view;

    public Model(View view) {
        this.view = view;
        executorService = Executors.newFixedThreadPool(6);
    }

    public void startTasks() {
        for (int i = 0; i < 6; i++) {
            executorService.execute(new Task(i));
        }
    }

    private class Task extends Thread {
        private int index;

        public Task(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            int priority = priorities[index];
            for (int i = 0; i <= 100; i++) {
                view.progressBars[index].setValue(i);
                try {
                    Thread.sleep(priority * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
