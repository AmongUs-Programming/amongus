package panel;

import thread.TimerThread;

import javax.swing.*;
import java.awt.*;

import static frame.StartFrame.SCREEN_HEIGHT;
import static frame.StartFrame.SCREEN_WIDTH;

public class ShowRolePanel extends JPanel {
    JPanel mainPanel;
    JLabel timer = new JLabel("5");
    String mapiaText = "당신은 마피아";
    String citizenText = "Loading...";
    String roleText;
    private Image backgroundImg;
    private JLabel roleLabel;
    public ShowRolePanel(){
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

//        if(getRole()==1){
//            roleText = mapiaText;
//        }else if(getRole()==0){
//            roleText = citizenText;
//        }
        roleLabel = new JLabel(roleText);
        roleLabel.setSize(200,300);
        roleLabel.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);


        mainPanel.add(timer);
        mainPanel.add(roleLabel);
        add(mainPanel);
    }
    public void runTimer(){
        TimerThread th = new TimerThread(timer,5);
        th.start();
    }

}
