package panel;

import client.Client;
import client.ClientFrame;
import frame.CreateRoomFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomListPanel extends JPanel {
    private ClientFrame clientFrame;
    private Client client;
    private JPanel panel = new JPanel();
    private JPanel topPanel = new JPanel();

    public RoomListPanel(ClientFrame clientFrame) {
        this.clientFrame = clientFrame;
        this.client = clientFrame.getClient();

        Border margin = BorderFactory.createEmptyBorder(10, 0, 20, 0);
        topPanel.setBorder(margin);
        setLayout(new BorderLayout());
        JLabel title = new JLabel("게임 방 리스트");
        JButton button = new JButton("방 생성");
        JLabel label = new JLabel(String.valueOf(clientFrame.getClient().getScore()));

        title.setFont(title.getFont().deriveFont(20.0f));
        title.setForeground(Color.BLACK);


        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setFont(title.getFont().deriveFont(15.0f));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
        button.setForeground(Color.BLACK);
        button.setBackground(null);

        topPanel.add(title);
        topPanel.add(button);
        topPanel.add(label);
        add(topPanel, BorderLayout.NORTH);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        topPanel.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateRoomFrame(clientFrame, client);
            }
        });
        client.sendMessage("303/roomlist");
        client.receiveMessage();
        String msg = client.getServerRealMessage();

        Dimension buttonSize = new Dimension(1270, 40);
        if(!msg.equals("null")){
            String[] strRoomList = msg.split(",");
            for (String room : strRoomList) {
                JButton enterRoomButton = new JButton(room);
                enterRoomButton.setPreferredSize(buttonSize);
                enterRoomButton.setMinimumSize(buttonSize);
                enterRoomButton.setMaximumSize(buttonSize);
                enterRoomButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,5));
                enterRoomButton.setFont(new Font("Arial",Font.BOLD,15));
                enterRoomButton.setBackground(Color.WHITE);
                enterRoomButton.setForeground(Color.BLACK);
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
