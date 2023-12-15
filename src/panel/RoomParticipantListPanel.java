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
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        add(title);

        String[] parts = msg.split(":");
        String[] participants = parts[1].split(",");
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 40));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setFont(title.getFont().deriveFont(15.0f));
            add(label);
        }
        setVisible(true);
    }

    public void updateMessage(String msg) {
        removeAll(); // 기존 라벨들을 제거
        JLabel title = new JLabel("참가자");
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(20.0f));
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        add(title);

        String[] parts = msg.split(":");
        String[] participants = parts[1].split(",");
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 30));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setFont(title.getFont().deriveFont(15.0f));
            add(label);
        }
        revalidate();
        repaint();
    }
}
