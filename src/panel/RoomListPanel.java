package panel;

import frame.CreateRoomFrame;
import frame.ParticipantSetNameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomListPanel extends JPanel {

    private static JPanel panel = new JPanel();
    private JSplitPane splitPane;
    private JButton button;

    public RoomListPanel(){
        panel.setLayout(new FlowLayout());
        panel.setBounds(0, 0, 1270, 680);
        JLabel title = new JLabel("게임 방 리스트");
        JButton button = new JButton("방 생성");
        panel.add(title);
        panel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dd");
                new CreateRoomFrame();
            }
        });
        add(panel);
        setVisible(true);
   }

   public static JPanel getPanel(){
        return panel;
   }

}
