package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.awt.*;

import static client.ClientFrame.SCREEN_HEIGHT;
import static client.ClientFrame.SCREEN_WIDTH;

public class GameOverPanel extends JPanel {
    JLabel timer = new JLabel("5");
    private Image backgroundImg;
    private JLabel resultLabel;
    ClientFrame clientFrame;

    public GameOverPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        setLayout(null);
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        backgroundImg = new ImageIcon(panel.ShowRolePanel.class.getResource("/images/backgroundImg.PNG")).getImage();

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        setVisible(true);

        resultLabel = new JLabel(String.valueOf(clientFrame.getClient().getScore()));
        resultLabel.setSize(200, 300);
        resultLabel.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);

        //mainPanel.add(timer);
        mainPanel.add(resultLabel);
        add(mainPanel);
        setVisible(true);
        runTimer();
    }

    public void runTimer() {
        TimerThread th = new TimerThread(clientFrame, timer, 1,"GAEM_OVER_PANEL");
        th.start();
    }
}
