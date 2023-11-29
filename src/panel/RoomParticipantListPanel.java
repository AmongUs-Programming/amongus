package panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class RoomParticipantListPanel extends JPanel {
    public RoomParticipantListPanel() {
        System.out.println("participant 입니다");
        JLabel label = new JLabel("사용자 목록");
        add(label);
        setVisible(true);

    }

}
