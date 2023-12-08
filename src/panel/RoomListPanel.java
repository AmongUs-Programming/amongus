package panel;

import User.UserInfo;
import frame.CreateRoomFrame;
import notUse.StartFrame;
import role.Role;
import room.Room;
import notUse.RoomList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import static notUse.MovePanel.roomListPanel;
import static notUse.MovePanel.showRolePanel;

public class RoomListPanel extends JPanel {

    private static JPanel panel = new JPanel();
    public static RoomList roomList;


    public RoomListPanel(){
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
                new CreateRoomFrame();
            }
        });

        //방에 참가자 넣기

        Map<Integer, Room> rooms = roomList.getRoomList();
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            Integer roomNumber = entry.getKey();
            Room room = entry.getValue();
            int paticipantNum = roomList.getParticipantList(entry.getKey()).getParticipantListSize();
            JPanel eachRoomPanel = new JPanel();
            JPanel paticipantNumPanel = new JPanel();
            ImageIcon peopleIcon = new ImageIcon("images/peopleIcon.png");
            eachRoomPanel.setLayout(new FlowLayout());
            paticipantNumPanel.setLayout(new FlowLayout());
            JLabel paticipantNumLabel = new JLabel(String.valueOf(paticipantNum));
            JLabel roomNumberLabel = new JLabel(String.valueOf(roomNumber));
            JLabel roomTitleLabel = new JLabel(room.getRoomTitle());
            paticipantNumPanel.add(new JLabel("hi"));
            paticipantNumPanel.add(paticipantNumLabel);
            eachRoomPanel.add(paticipantNumLabel);
            eachRoomPanel.add(paticipantNumLabel);
            eachRoomPanel.add(roomNumberLabel);
            eachRoomPanel.add(roomTitleLabel);
            eachRoomPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //선택한 해당 RoomPanel에 이동하기
//                  StartFrame.setPanel(roomListPanel,new RoomPanel(roomList.getParticipantList(entry.getKey()).getOwner(),roomNumber));
                    RoomListPanel.roomList.getParticipantList(entry.getKey()).addParticipant(UserInfo.getName());
                    new Role(roomList.getParticipantList(entry.getKey()).getParticipants());
                    StartFrame.setPanel(roomListPanel,showRolePanel);
                    showRolePanel.runTimer();
                }
            });
            eachRoomPanel.setVisible(true);
            panel.add(eachRoomPanel);
            System.out.println("Room Number: " + roomNumber + ", Room: " + room.getRoomTitle());
        }
//
        add(panel);
        setVisible(true);
   }

}
