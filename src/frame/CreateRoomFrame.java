package frame;

import User.UserInfo;
import panel.RoomListPanel;
import panel.RoomPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                setVisible(false);
                StartFrame.setPanel(RoomListPanel.getPanel(),new RoomPanel(owner));
            }
        });
        setVisible(true);
    }
}
