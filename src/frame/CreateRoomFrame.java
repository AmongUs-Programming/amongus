package frame;

import User.UserInfo;
import panel.RoomListPanel;
import panel.RoomPanel;
import room.RoomList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static movePanel.MovePanel.roomListPanel;

public class CreateRoomFrame extends JFrame {
    String owner;
    public CreateRoomFrame(){
        super("CreateRoom");
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JLabel notice = new JLabel("방 이름 입력 후 엔터 눌러주세요");
        JTextField textField = new JTextField(10);
        add(notice);
        add(textField);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField) e.getSource();
                owner = UserInfo.getName();
                //방 생성
                RoomListPanel.roomList.addRoom(1,t.getText());
                //방에 참가자 넣기
                RoomListPanel.roomList.getParticipantList(1).addParticipant(UserInfo.getName());
                setVisible(false);
                StartFrame.setPanel(roomListPanel,new RoomPanel(owner,1));
            }
        });
        setVisible(true);
    }
}
