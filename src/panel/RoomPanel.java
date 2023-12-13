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
        clientFrame.getClient().receiveMessage();
        message = clientFrame.getClient().getServerRealMessage();
        client = clientFrame.getClient();
        System.out.println("message: " + message);

        setLayout(new BorderLayout()); // GridLayout을 사용하여 왼쪽, 오른쪽 패널을 가로로 나란히 배치합니다.

        client.sendMessage("202/" + "userName");
        client.receiveMessage();
        userName = client.getServerRealMessage();

        System.out.println("내 이름" + userName);
        String roomTitle = clientFrame.getRoomTitle();
        participantListPanel = new RoomParticipantListPanel(clientFrame, message, roomTitle);
        RoomChatPanel roomchatPanel = new RoomChatPanel(userName);

        MessageThread messageThread = new MessageThread();
        messageThread.start();

        JButton startBtn = new JButton("GAME START");
        startBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        startBtn.setFont(new Font("Arial",Font.BOLD,15));
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);
        Dimension startBtnSize = new Dimension(1270, 40);
        startBtn.setPreferredSize(startBtnSize);
        startBtn.setMinimumSize(startBtnSize);
        startBtn.setMaximumSize(startBtnSize);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageThread.interrupt();
                clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
            }
        });

        add(startBtn, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1,2));
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(participantListPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        roomchatPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.add(roomchatPanel, BorderLayout.CENTER); // chatPanel을 오른쪽 패널에 추가

        leftPanel.setPreferredSize(new Dimension(600, 600));
        rightPanel.setPreferredSize(new Dimension(500, 600));

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }


    public class MessageThread extends Thread {
        @Override
        public void run() {
            clientFrame.getClient().receiveMessage();
            String message2 = clientFrame.getClient().getServerRealMessage();
            System.out.println("message2: " + message2);
            if (!message2.equals(message)) {
                message = message2;
                System.out.println(message);
                participantListPanel.updateMessage(message);
                new RoomChatPanel(userName);
            }

        }
    }
}


