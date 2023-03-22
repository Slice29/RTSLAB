import javax.swing.*;

public class View {
    private JFrame mainFrame;
    public static JProgressBar[] progressBars;
    private JButton startButton;

    public View() {
        mainFrame = new JFrame("Progress Bars Example");
        mainFrame.setSize(600, 800);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        progressBars = new JProgressBar[6];
        for (int i = 0; i < 6; i++) {
            progressBars[i] = new JProgressBar(0, 100);
            progressBars[i].setBounds(50, 100 * (i + 1), 500, 50);
            progressBars[i].setStringPainted(true);
            mainFrame.add(progressBars[i]);
        }

        startButton = new JButton("Start");
        startButton.setBounds(50, 50, 100, 50);

        mainFrame.add(startButton);

        mainFrame.setVisible(true);
    }

    public JButton getStartButton() {
        return startButton;
    }
}
