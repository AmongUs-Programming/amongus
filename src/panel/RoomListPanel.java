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

    public RoomListPanel(ClientFrame clientFrame) {
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
                new CreateRoomFrame(clientFrame, client);
            }
        });
        client.sendMessage("303/roomlist");
        client.receiveMessage();
        String msg = client.getServerRealMessage();
        if(!msg.equals(null)){
            String[] strRoomList = msg.split(",");
            for (String room : strRoomList) {
                JButton enterRoomButton = new JButton(room);
                enterRoomButton.addActionListener(e -> {
                    // 버튼이 클릭되면 해당 방에 입장하도록 서버에 요청
                    client.sendMessage("302/" + room);
                    client.sendMessage("401/" + room);
                    clientFrame.setRoomTitle(room);
                    clientFrame.setPanelState(ClientFrame.PanelState.ROOM_PANEL);
                });
                panel.add(enterRoomButton);
            }
        }
        add(panel);
        setVisible(true);
    }

}
