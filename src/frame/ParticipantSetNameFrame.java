package frame;

import client.Client;
import client.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ParticipantSetNameFrame extends JFrame {
    private ClientFrame clientFrame;
    private Client client;
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
                System.out.println(name);
                setVisible(false);
                if(connectClient(name)){
                    clientFrame.setPanelState(ClientFrame.PanelState.ROOM_LIST_PANEL);  // 상태를 ROOM_LIST_PANEL로 변경
                }
            }
        });
        setVisible(true);
    }
    private Boolean connectClient(String name) {
        client = new Client("127.0.0.1", 29998,name);
        client.sendMessage("TEXT");
        client.sendMessage("200/"+name);
        clientFrame.setClient(client);  // ClientFrame에 Client 객체 저장
        client.receiveMessage();
        if(client.receiveMessage().equals(null)){
            return false;
        }else return true;
    }
}
