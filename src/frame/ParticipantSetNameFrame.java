package frame;

import User.UserInfo;
import client.Client;
import client.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ParticipantSetNameFrame extends JFrame {
    private ClientFrame clientFrame;
    public ParticipantSetNameFrame(ClientFrame clientFrame){
        super("ParticipantSetName");
        this.clientFrame=clientFrame;
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JLabel notice = new JLabel("유저 이름을 입력 후 엔터 눌러주세요");
        JTextField textField = new JTextField(10);
        add(notice);
        add(textField);

        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField t = (JTextField) e.getSource();
                String name = t.getText();
                new UserInfo(name);
                System.out.println(name);
                setVisible(false);
                connectClient(name);
            }
        });
        setVisible(true);
    }
    private void connectClient(String name) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                Client client = new Client("127.0.0.1", 29998);
                client.sendMessage("200/"+name);
                clientFrame.setClient(client);  // ClientFrame에 Client 객체 저장
                clientFrame.setPanelState(ClientFrame.PanelState.ROOM_LIST_PANEL);  // 상태를 ROOM_LIST_PANEL로 변경

                return null;
            }
        };
        worker.execute();
    }
}
