package panel;

import client.ClientFrame;

import javax.swing.*;
import java.awt.*;

public class RoomPanel extends JPanel {
    //Boolean condition=false;
    String owner;
    public RoomPanel(ClientFrame clientFrame, String roomTitle) {

        // RoomPanel에 leftPanel과 rightPanel을 추가합니다.
        setLayout(new BorderLayout()); // GridLayout을 사용하여 왼쪽, 오른쪽 패널을 가로로 나란히 배치합니다.
        String msg = clientFrame.getClient().receiveMessage();
        System.out.println("msg"+msg);
        if(!msg.equals(null)){
            // RoomParticipantListPanel과 RoomChatPanel을 생성
            RoomParticipantListPanel participantListPanel = new RoomParticipantListPanel(clientFrame,msg);
//            RoomChatPanel roomchatPanel = new RoomChatPanel();

            // 왼쪽에 배치될 panel
            JPanel leftPanel = new JPanel(new BorderLayout());
            add(leftPanel, BorderLayout.WEST); // RoomPanel의 서쪽에 leftPanel 추가
            leftPanel.add(participantListPanel, BorderLayout.WEST); // participantListPanel을 왼쪽 패널에 추가

//
//            // 오른쪽에 배치될 panel
//            JPanel rightPanel = new JPanel(new BorderLayout());
//            add(rightPanel, BorderLayout.CENTER);
//            roomchatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//            rightPanel.add(roomchatPanel, BorderLayout.CENTER); // chatPanel을 오른쪽 패널에 추가
//
//            leftPanel.setPreferredSize(new Dimension(600, 600));
//            rightPanel.setPreferredSize(new Dimension(500, 600));
        }
        setVisible(true);

    }


}


