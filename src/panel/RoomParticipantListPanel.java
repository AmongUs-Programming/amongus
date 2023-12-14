package panel;

import client.ClientFrame;
import javax.swing.*;
import java.awt.*;

public class RoomParticipantListPanel extends JPanel {
    public RoomParticipantListPanel(ClientFrame clientFrame,String msg,String roomTitle) {

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("참가자");
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(20.0f)); // 현재 폰트를 유지하며 크기만 변경
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
        title.setHorizontalAlignment(JLabel.CENTER);// 수평 가운데 정렬
        title.setVerticalAlignment(JLabel.CENTER);// 수직 가운데 정렬
        add(title);

        String[] parts = msg.split(":"); // ":" 기준으로 문자열을 나눕니다.
        String[] participants = parts[1].split(","); // 참가자 이름을 "," 기준으로 나누어 배열로 만듭니다.
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 40));
            label.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
            label.setHorizontalAlignment(JLabel.CENTER);// 수평 가운데 정렬
            label.setVerticalAlignment(JLabel.CENTER);// 수직 가운데 정렬
            label.setFont(title.getFont().deriveFont(15.0f)); // 현재 폰트를 유지하며 크기만 변경
            add(label);
        }
        setVisible(true);
    }

    public void updateMessage(String msg) {
        removeAll(); // 기존 라벨들을 제거
        JLabel title = new JLabel("참가자");
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(20.0f)); // 현재 폰트를 유지하며 크기만 변경
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
        title.setHorizontalAlignment(JLabel.CENTER);     // 수평 가운데 정렬
        title.setVerticalAlignment(JLabel.CENTER);       // 수직 가운데 정렬
        add(title);

        String[] parts = msg.split(":"); // ":" 기준으로 문자열을 나눕니다.
        String[] participants = parts[1].split(","); // 참가자 이름을 "," 기준으로 나누어 배열로 만듭니다.
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 30));
            label.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
            label.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 정렬
            label.setVerticalAlignment(JLabel.CENTER); // 수직 가운데 정렬
            label.setFont(title.getFont().deriveFont(15.0f)); // 현재 폰트를 유지하며 크기만 변경
            add(label);
        }
        revalidate();
        repaint();
    }
}
