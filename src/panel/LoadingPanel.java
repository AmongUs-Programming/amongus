package panel;

import client.Client;
import client.ClientFrame;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingPanel extends JPanel {
    private Image backgroundImage;
    private ClientFrame clientFrame;
    private Client client;

    public LoadingPanel(ClientFrame clientFrame) {
        String roomTitle = clientFrame.getRoomTitle();
        this.clientFrame = clientFrame;
        client = clientFrame.getClient();
        backgroundImage = new ImageIcon(Main.class.getResource("/images/gamebg.png")).getImage();

        // JLabel 설정
        JLabel loadingLabel = new JLabel("Loading...");
        loadingLabel.setForeground(Color.WHITE);
        loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(loadingLabel, BorderLayout.CENTER);
        loadingLabel.setFont(loadingLabel.getFont().deriveFont(25.0f));
        //clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("500/"+roomTitle);
                clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
            }
        });
        timer.setRepeats(false); // Timer가 한 번만 실행되도록 설정
        timer.start(); // Timer 시작
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경 이미지 그리기
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
