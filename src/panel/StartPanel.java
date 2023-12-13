package panel;

import client.ClientFrame;
import frame.ParticipantSetNameFrame;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static client.ClientFrame.SCREEN_HEIGHT;
import static client.ClientFrame.SCREEN_WIDTH;

public class StartPanel extends JPanel {
    private Image backgroundImg;
    private JButton startButton;
    private ClientFrame clientFrame;

    public StartPanel(ClientFrame clientFrame){
        this.clientFrame = clientFrame;
        setLayout(null);
        setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // 배경 이미지 로드
        backgroundImg = new ImageIcon(Main.class.getResource("/images/backgroundImg.PNG")).getImage();

        // 시작 버튼 생성
        startButton = new JButton("시작하기");
        startButton.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ParticipantSetNameFrame(clientFrame);
                //세으나 밑에꺼 주석처리 풀면 됑
                //clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
            }
        });
        add(startButton); // 패널에 버튼 추가
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg, 0, 0, null);
    }
}
