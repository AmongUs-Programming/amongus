package panel;

import client.Client;
import client.ClientFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomPanel extends JPanel {
    //Boolean condition=false;
    String owner;
    private RoomParticipantListPanel participantListPanel;
    private String message;
    private ClientFrame clientFrame;
    private String userName;
    private Client client;

    public RoomPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        client = clientFrame.getClient();
        clientFrame.getClient().receiveMessage();
        message = clientFrame.getClient().getServerRealMessage();
        System.out.println("message: " + message);
        MessageThread messageThread = new MessageThread();
        messageThread.start();
        setLayout(new BorderLayout()); // GridLayout을 사용하여 왼쪽, 오른쪽 패널을 가로로 나란히 배치합니다.

        userName = client.getName();
        String roomTitle = clientFrame.getRoomTitle();
        participantListPanel = new RoomParticipantListPanel(clientFrame, message, roomTitle);
        // 왼쪽에 배치될 panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        add(leftPanel, BorderLayout.WEST); // RoomPanel의 서쪽에 leftPanel 추가
        leftPanel.add(participantListPanel, BorderLayout.WEST); // participantListPanel을 왼쪽 패널에 추가
        leftPanel.setPreferredSize(new Dimension(600, 600));
        leftPanel.setPreferredSize(new Dimension(600, 600));


        JButton startBtn = new JButton("게임시작");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                messageThread.interrupt();
                clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);

            }
        });
        add(startBtn, BorderLayout.NORTH);

//
//            // 오른쪽에 배치될 panel
        //        RoomChatPanel roomchatPanel = new RoomChatPanel(userName);
//        JPanel rightPanel = new JPanel(new BorderLayout());
//        add(rightPanel, BorderLayout.CENTER);
//        roomchatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//        rightPanel.add(roomchatPanel, BorderLayout.CENTER); // chatPanel을 오른쪽 패널에 추가
//
//            rightPanel.setPreferredSize(new Dimension(500, 600));
//        rightPanel.setPreferredSize(new Dimension(500, 600));


        setVisible(true);

    }

    public class MessageThread extends Thread {
        @Override
        public void run() {
//            clientFrame.getClient().sendMessage("401/"+clientFrame.getRoomTitle());
            clientFrame.getClient().receiveMessage();
            String message2 = clientFrame.getClient().getServerRealMessage();
            System.out.println("message2: " + message2);
            if (!message2.equals(message)) {
                message = message2;
                System.out.println(message);
                participantListPanel.updateMessage(message);
            }
        }
    }
}


