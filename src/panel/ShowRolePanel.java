package panel;

import frame.Main;
import frame.ParticipantSetNameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static frame.StartFrame.SCREEN_HEIGHT;
import static frame.StartFrame.SCREEN_WIDTH;

public class ShowRolePanel extends JPanel {
    JPanel mainPanel;
    private Image backgroundImg;
    private JLabel roleLabel;
    public ShowRolePanel(){
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, null);
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        add(mainPanel);

        // 배경 이미지 로드
        backgroundImg = new ImageIcon(Main.class.getResource("/images/backgroundImg.PNG")).getImage();

        // 시작 버튼 생성
        roleLabel = new JLabel("시작하기");
        roleLabel.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);
        mainPanel.add(roleLabel); // 패널에 버튼 추가
    }
}
