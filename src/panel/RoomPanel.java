package panel;

import client.Client;
import client.ClientFrame;
import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RoomPanel extends JPanel {
    private RoomParticipantListPanel participantListPanel;
    private String message;
    private ClientFrame clientFrame;
    private String userName;
    private Client client;
    private  MessageThread messageThread;
    private Boolean isOwner;
    public static volatile boolean running = true;


    public RoomPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        client = clientFrame.getClient();
        clientFrame.getClient().receiveMessage();
        message = clientFrame.getClient().getServerRealMessage();
        System.out.println("message: " + message);
        messageThread = new MessageThread();
        messageThread.start();
        client.sendMessage("400/"+clientFrame.getRoomTitle());

        setLayout(new BorderLayout());

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
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        //준비 라벨
        JLabel readylb = new JLabel("게임 대기중...");
        readylb.setBackground(Color.BLACK);
        readylb.setForeground(Color.WHITE);
        readylb.setFont(readylb.getFont().deriveFont(15.0f));
        Dimension readySize = new Dimension(1270,40);
        readylb.setPreferredSize(readySize);
        readylb.setMinimumSize(readySize);
        readylb.setMaximumSize(readySize);
        // 시작하기 버튼
        JButton startBtn = new JButton("게임 시작");
        startBtn.setBackground(Color.BLACK);
        startBtn.setForeground(Color.WHITE);
        startBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        startBtn.setFont(startBtn.getFont().deriveFont(15.0f));
        Dimension buttonSize = new Dimension(1270, 40);
        startBtn.setPreferredSize(buttonSize);
        startBtn.setMinimumSize(buttonSize);
        startBtn.setMaximumSize(buttonSize);

        if(isOwner){
            add(startBtn, BorderLayout.NORTH);
        }
        else{
            add(readylb, BorderLayout.NORTH);
        }

        startBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("504/"+ roomTitle);
                client.sendMessage("500/"+roomTitle);
            }
        });

        add(centerPanel, BorderLayout.CENTER);


        setVisible(true);
    }



    public class MessageThread extends Thread {

        @Override
        public void run() {
            while (running){
                clientFrame.getClient().receiveMessage();
                String message2 = clientFrame.getClient().getServerRealMessage();
                System.out.println("message1: "+message);
                System.out.println("message2: "+message2);
                if (!message2.equals(message)) {
                    if(message2.equals("CHANGEPANEL")){
                        running=false;
                        break;
                    }
                    else if(message2.startsWith("OWNER")){
                        String owner = message2.split(":")[1];
                        if(owner.equals(client.getName())){
                            isOwner=true;
                        }else {
                            isOwner=false;
                        }
                        System.out.println("check : "+isOwner);
                    } else if (message2.startsWith("ITEM")) {
                        clientFrame.setItemLocation(message2);
                        System.out.println("504 출력 : "+message2);
                    } else {
                        System.out.println("else");
                        message = message2;
                        System.out.println(message);
                        participantListPanel.updateMessage(message);
                    }
                }
                System.out.println("running");
            }
            if (!running) {
                clientFrame.setPanelState(ClientFrame.PanelState.LOADING_PANEL);
            }

        }
    }
}

