package panel;

import client.Client;
import client.ClientFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;

public class RoomPanel extends JPanel {
    //Boolean condition=false;
    String owner;
    private RoomParticipantListPanel participantListPanel;
    private String message;
    private ClientFrame clientFrame;
    private String userName;
    private Client client;
    private  MessageThread messageThread;

    public RoomPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        client = clientFrame.getClient();
        clientFrame.getClient().receiveMessage();
        message = clientFrame.getClient().getServerRealMessage();
        System.out.println("message: " + message);
        messageThread = new MessageThread();
        messageThread.start();

        setLayout(new BorderLayout()); // 전체 패널을 BorderLayout으로 설정

        // 왼쪽 패널 (participantListPanel 포함)
        userName = client.getName();
        String roomTitle = clientFrame.getRoomTitle();
        participantListPanel = new RoomParticipantListPanel(clientFrame, message, roomTitle);
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(participantListPanel, BorderLayout.CENTER);

        // 오른쪽 패널 (roomchatPanel 포함)
        JavaChatClientViewPanel roomchatPanel = new JavaChatClientViewPanel(userName);
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(roomchatPanel, BorderLayout.CENTER);

        // 중앙 패널 (왼쪽 패널과 오른쪽 패널을 반반씩 배치)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2)); // 1행 2열 그리드 레이아웃
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        // 시작하기 버튼
        JButton startBtn = new JButton("게임 시작");
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);
        startBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        startBtn.setFont(startBtn.getFont().deriveFont(15.0f)); // 현재 폰트를 유지하며 크기만 변경
        Dimension buttonSize = new Dimension(1270, 40);
        startBtn.setPreferredSize(buttonSize);
        startBtn.setMinimumSize(buttonSize);
        startBtn.setMaximumSize(buttonSize);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("500/"+roomTitle);
            }
        });

        // 전체 패널에 추가
        add(centerPanel, BorderLayout.CENTER);
        add(startBtn, BorderLayout.NORTH);

        setVisible(true);
    }



    public class MessageThread extends Thread {
        @Override
        public void run() {
            while (true){
                clientFrame.getClient().receiveMessage();
                String message2 = clientFrame.getClient().getServerRealMessage();
                System.out.println("message1: "+message);
                System.out.println("message2: "+message2);
                if (!message2.equals(message)) {
                    if(message2.equals("CHANGEPANEL")){
                        clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
                    }else {
                        message = message2;
                        System.out.println(message);
                        participantListPanel.updateMessage(message);
                    }
                }
            }
        }
    }
}

