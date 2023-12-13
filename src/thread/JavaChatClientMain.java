package thread;//JavaChatClientMain.java
//Java Client 시작import java.awt.BorderLayout;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JavaChatClientMain extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaChatClientMain frame = new JavaChatClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


			String username = "";
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			JavaChatClientViewPanel view = new JavaChatClientViewPanel(username, ip_addr, port_no);

}
