package frame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame {

    public static final int SCREEN_WIDTH = 1270;
    public static final int SCREEN_HEIGHT = 630;

    public static JFrame frame = new JFrame("Among Us");
    private Image backgroundImg;
    private JButton startButton;
    private JPanel mainPanel;

    public StartFrame() {
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // 레이아웃 매니저를 사용하지 않기 위해 null 설정

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, null);
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.add(mainPanel);

        // 배경 이미지 로드
        backgroundImg = new ImageIcon(Main.class.getResource("/images/backgroundImg.PNG")).getImage();

        // 시작 버튼 생성
        startButton = new JButton("시작하기");
        startButton.setBounds(SCREEN_WIDTH / 2 - 50, SCREEN_HEIGHT - 130, 100, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("게임이 시작됩니다.");
            }
        });
        mainPanel.add(startButton); // 패널에 버튼 추가

        frame.setVisible(true);
    }
    public static void setPanel(JPanel currentPanel,JPanel changePanel){
        frame.remove(currentPanel);
        frame.add(changePanel);
        frame.revalidate();
        frame.repaint();
    }
}