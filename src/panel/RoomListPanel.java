package panel;

import client.Client;
import client.ClientFrame;
import frame.CreateRoomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomListPanel extends JPanel {
    private ClientFrame clientFrame;
    private Client client;
    private JPanel panel = new JPanel();

    public RoomListPanel(ClientFrame clientFrame){
        this.clientFrame = clientFrame;
        this.client = clientFrame.getClient();
//        roomList = new RoomList();
        panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 1270, 680);

        JLabel title = new JLabel("게임 방 리스트");
        JButton button = new JButton("방 생성");

        panel.add(title);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateRoomFrame(clientFrame,client);
            }
        });
        client.sendMessage("303/ ");
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // 서버로부터 응답을 기다립니다.
                    String msg = client.receiveMessage();
                    System.out.println("listPAnel: "+msg);
                    publish(msg);  // publish 메서드를 사용하여 결과를 처리합니다.

                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                // UI를 업데이트 합니다. 이 메서드는 EDT(Event Dispatch Thread)에서 실행됩니다.
                for(String msg : chunks) {
                    JLabel showRoom = new JLabel(msg);
                    panel.add(showRoom);
                    panel.revalidate();
                    System.out.println("Received message: " + msg);
                }
            }
        };
        worker.execute();
//        Map<Integer, Room> rooms = roomList.getRoomList();
//        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
//            Integer roomNumber = entry.getKey();
//            Room room = entry.getValue();
//            int paticipantNum = roomList.getParticipantList(entry.getKey()).getParticipantListSize();
//            JPanel eachRoomPanel = new JPanel();
//            JPanel paticipantNumPanel = new JPanel();
//            ImageIcon peopleIcon = new ImageIcon("images/peopleIcon.png");
//            eachRoomPanel.setLayout(new FlowLayout());
//            paticipantNumPanel.setLayout(new FlowLayout());
//            JLabel paticipantNumLabel = new JLabel(String.valueOf(paticipantNum));
//            JLabel roomNumberLabel = new JLabel(String.valueOf(roomNumber));
//            JLabel roomTitleLabel = new JLabel(room.getRoomTitle());
//            paticipantNumPanel.add(new JLabel("hi"));
//            paticipantNumPanel.add(paticipantNumLabel);
//            eachRoomPanel.add(paticipantNumLabel);
//            eachRoomPanel.add(paticipantNumLabel);
//            eachRoomPanel.add(roomNumberLabel);
//            eachRoomPanel.add(roomTitleLabel);
//            eachRoomPanel.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    //선택한 해당 RoomPanel에 이동하기
////                  StartFrame.setPanel(roomListPanel,new RoomPanel(roomList.getParticipantList(entry.getKey()).getOwner(),roomNumber));
//                    RoomListPanel.roomList.getParticipantList(entry.getKey()).addParticipant(UserInfo.getName());
//                    new Role(roomList.getParticipantList(entry.getKey()).getParticipants());
//                    StartFrame.setPanel(roomListPanel,showRolePanel);
//                    showRolePanel.runTimer();
//                }
//            });
//            eachRoomPanel.setVisible(true);
//            panel.add(eachRoomPanel);
//            System.out.println("Room Number: " + roomNumber + ", Room: " + room.getRoomTitle());
//        }
//
        add(panel);
        setVisible(true);
   }

}
