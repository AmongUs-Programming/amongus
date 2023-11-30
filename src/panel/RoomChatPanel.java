package panel;

import User.UserInfo;
import thread.JavaChatClientView;

import java.awt.*;
import javax.swing.*;


public class RoomChatPanel extends JPanel {
    JPanel panel ;

    String username = UserInfo.getName();
    String ip_addr = "127.0.0.1";
    String port_no = "30000";
    JavaChatClientView view = new JavaChatClientView(username, ip_addr, port_no);
       public RoomChatPanel(){
           setLayout(new BorderLayout());

           JavaChatClientView chatView = new JavaChatClientView(username, ip_addr, port_no);
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