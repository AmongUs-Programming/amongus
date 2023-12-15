package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.awt.*;

import static client.ClientFrame.SCREEN_HEIGHT;
import static client.ClientFrame.SCREEN_WIDTH;


public class ShowRolePanel extends JPanel {
    JLabel timer = new JLabel("5");
    String mapiaText = "당신은 마피아";
    String citizenText = "Loading...";
    String roleText;
    private Image backgroundImg;
    private JLabel roleLabel;
    ClientFrame clientFrame;
    public ShowRolePanel(ClientFrame clientFrame){
        this.clientFrame=clientFrame;
        setLayout(null);
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        backgroundImg = new ImageIcon(ShowRolePanel.class.getResource("/images/backgroundImg.PNG")).getImage();

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

        String role = clientFrame.getRole();
        if(role.equals("IMPOSTER")){
            roleText=mapiaText;
        }else {
            roleText=citizenText;
        }

        roleLabel = new JLabel(roleText);
        roleLabel.setSize(200,300);
        roleLabel.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);

        //mainPanel.add(timer);
        mainPanel.add(roleLabel);
        add(mainPanel);
        setVisible(true);
        runTimer();
    }
    public void runTimer(){
        TimerThread th = new TimerThread(clientFrame,timer,1,"SHOW_ROLE_PANEL");
        th.start();
    }

}
