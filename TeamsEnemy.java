import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamsEnemy extends JFrame {
    private JButton startButton;
    private JButton stopButton;
    private volatile boolean running = false;
    private Thread workerThread;

    public TeamsEnemy() {
        setTitle("Teams Enemy");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Buttons
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        add(startButton);
        add(stopButton);

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About...");
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // About dialog action
        aboutItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Copyright 2025 - Panagiotis Fragos", "About Teams Enemy", JOptionPane.INFORMATION_MESSAGE);
        });

        // Start button action
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!running) {
                    running = true;
                    startMouseMovement();
                }
            }
        });

        // Stop button action
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = false;
            }
        });
    }

    private void startMouseMovement() {
        workerThread = new Thread(() -> {
            try {
                Robot robot = new Robot();
                int step = 150;
                boolean forward = true;

                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                Point startPoint = pointerInfo.getLocation();
                int baseX = (int) startPoint.getX();
                int baseY = (int) startPoint.getY();

                while (running) {
                    int x = forward ? baseX + step : baseX - step;
                    robot.mouseMove(x, baseY);
                    robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(10000);
                    forward = !forward;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        workerThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TeamsEnemy app = new TeamsEnemy();
            app.setVisible(true);
        });
    }
}
