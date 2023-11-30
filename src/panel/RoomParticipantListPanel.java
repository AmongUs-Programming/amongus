package panel;

import User.UserInfo;
import frame.StartFrame;
import participant.Participant;
import participant.ParticipantList;
import room.Room;
import room.RoomList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static movePanel.MovePanel.roomListPanel;
import static panel.RoomListPanel.roomList;

public class RoomParticipantListPanel extends JPanel {
    private static JPanel panel = new JPanel();
    public static ParticipantList participantList;

    public RoomParticipantListPanel(int roomID) {
        participantList = new ParticipantList();
        panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 1270, 680);

        System.out.println(RoomListPanel.roomList);
        System.out.printf(roomList.getRoomList().toString());
        ArrayList<String> list = new ArrayList<>();
        list.add("user1");//System.out.println("리스트 크기" + list.size());
        list.add("user2");//System.out.println("리스트 크기" + list.size());

        JLabel userName = new JLabel();

    }

}

//for(int i = 0; i < list.size(); i++) {
//        System.out.println("이름 " + list.get(i));
//        String currentUser = list.get(i);
//        JPanel userInfo = new JPanel();
//        JLabel userName = new JLabel(currentUser);
//        add(userInfo);
//        add(userName);
//        setVisible();
//        }
