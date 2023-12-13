package frame;

import client.Client;
import client.ClientFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateRoomFrame extends JFrame {
    String owner;
    String roomTitle;
    private Client client;
    private ClientFrame clientFrame;
    public CreateRoomFrame(ClientFrame clientFrame,Client client){
        super("CreateRoom");
        this.client=client;
        this.clientFrame=clientFrame;
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
                roomTitle = t.getText().toString();
                System.out.println("roomTitle"+roomTitle);
                client.sendMessage("300/"+roomTitle);

                clientFrame.setPanelState(ClientFrame.PanelState.ROOM_PANEL);
                clientFrame.setRoomTitle(roomTitle);

                setVisible(false);
            }
        });
        setVisible(true);
    }
}
