package panel;

import client.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomPanel extends JPanel {
    //Boolean condition=false;
    String owner;
    private RoomParticipantListPanel participantListPanel;
    private String message;
    private ClientFrame clientFrame;

    public RoomPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        message = clientFrame.getClient().receiveMessage();
        System.out.println("message: " + message);
        MessageThread messageThread = new MessageThread();
        messageThread.start();
        // RoomPanel에 leftPanel과 rightPanel을 추가합니다.
        setLayout(new BorderLayout()); // GridLayout을 사용하여 왼쪽, 오른쪽 패널을 가로로 나란히 배치합니다.
        JButton startBtn = new JButton("게임시작");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageThread.interrupt();
                clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);

            }
        });
        add(startBtn);
        // RoomParticipantListPanel과 RoomChatPanel을 생성
        String roomTitle = clientFrame.getRoomTitle();
        participantListPanel = new RoomParticipantListPanel(clientFrame, message, roomTitle);
//            RoomChatPanel roomchatPanel = new RoomChatPanel(clientFrame);

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

        leftPanel.setPreferredSize(new Dimension(600, 600));
//            rightPanel.setPreferredSize(new Dimension(500, 600));


        setVisible(true);

    }

    public class MessageThread extends Thread {
        @Override
        public void run() {
            String message2 = clientFrame.getClient().receiveMessage();
            System.out.println("message2: " + message2);
            if (!message2.equals(message)) {
                message = message2;
                System.out.println(message);
                participantListPanel.updateMessage(message);
            }
        }
    }
}


