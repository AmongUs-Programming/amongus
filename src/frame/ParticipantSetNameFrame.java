package frame;

import User.UserInfo;
import client.Client;
import client.ClientFrame;
import notUse.StartFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static notUse.MovePanel.roomListPanel;
import static notUse.MovePanel.startPanel;

public class ParticipantSetNameFrame extends JFrame {

    public ParticipantSetNameFrame(){
        super("ParticipantSetName");
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
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        // Connect to the server in the background
                        Client client = new Client("127.0.0.1", 29998);
                        client.sendMessage("200/"+name);
                        ClientFrame.isStartPanel=true;
                        ClientFrame.isChange=true;
                        return null;
                    }
                };

                // Execute the SwingWorker
                worker.execute();
            }
        });
        setVisible(true);
    }
}
