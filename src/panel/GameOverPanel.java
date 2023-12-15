package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.awt.*;

import static client.ClientFrame.SCREEN_HEIGHT;
import static client.ClientFrame.SCREEN_WIDTH;

public class GameOverPanel extends JPanel {
    JLabel timer = new JLabel("5");
    String winnerText = "당신은 마피아";
    String loseText = "Loading...";
    String resultText;
    private Image backgroundImg;
    private JLabel resultLabel;
    ClientFrame clientFrame;

    public GameOverPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        clientFrame.getClient().sendMessage("503/"+clientFrame.getRoomTitle());
        clientFrame.getClient().receiveMessage();
        String msg = clientFrame.getClient().getServerRealMessage();
        resultText = msg;
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

        resultLabel = new JLabel(resultText);
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
