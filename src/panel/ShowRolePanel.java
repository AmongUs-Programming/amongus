package panel;

import frame.Main;
import frame.ParticipantSetNameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static frame.StartFrame.SCREEN_HEIGHT;
import static frame.StartFrame.SCREEN_WIDTH;

public class ShowRolePanel extends JPanel {
    JPanel mainPanel;
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
        roleLabel = new JLabel("당신은 마피아");
        roleLabel.setSize(200,300);
        roleLabel.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);
        mainPanel.add(roleLabel);
        add(mainPanel);
    }
}
