package panel;

import client.Client;
import client.ClientFrame;
import thread.JavaChatClientViewPanel;

import java.awt.*;
import javax.swing.*;


public class RoomChatPanel extends JPanel {
    JPanel panel ;
    ClientFrame clientFrame;
    String ip_addr = "127.0.0.1";
    String port_no = "30000";
    String userName;
    private Client client;

       public RoomChatPanel(String userName){
           client = clientFrame.getClient();
           //client.sendMessage("202/"+"userName");
           //userName = client.receiveMessage();
           //System.out.println("내 이름"+userName);
           //this.userName = userName;
           //this.clientFrame = clientFrame;
           setLayout(new BorderLayout());

           //username = clientFrame.getName();
           JavaChatClientViewPanel chatView = new JavaChatClientViewPanel(userName, ip_addr, port_no);
           add(chatView, BorderLayout.CENTER);

           //setLayout(new FlowLayout());
           //add(new JLabel("ddd"));
           //panel = new JPanel();
           //panel.add(view);
           //panel.setVisible(true);
           //setVisible(true);
        }
        public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        thread.JavaChatClientMain frame = new thread.JavaChatClientMain();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


}