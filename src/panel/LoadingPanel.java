package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.awt.*;

import static client.ClientFrame.SCREEN_HEIGHT;
import static client.ClientFrame.SCREEN_WIDTH;


public class LoadingPanel extends JPanel {
    JLabel timer = new JLabel("5");
    String loadingText = "Loading...";
    private Image backgroundImg;
    ClientFrame clientFrame;
    public LoadingPanel(ClientFrame clientFrame){
        this.clientFrame=clientFrame;
        setLayout(null);
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        backgroundImg = new ImageIcon(LoadingPanel.class.getResource("/images/backgroundImg.PNG")).getImage();

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

        JLabel label = new JLabel(loadingText);
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(20.0f));
        label.setSize(200,300);
        label.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);

        mainPanel.add(label);
        add(mainPanel);
        setVisible(true);
        runTimer();
    }
    public void runTimer(){
        TimerThread th = new TimerThread(clientFrame,timer,1,"LOADING_PANEL");
        th.start();
    }

}
