package panel;

import client.ClientFrame;
//import server.Participant;
//import notUse.ParticipantList;
//import notUse.RoomList;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RoomParticipantListPanel extends JPanel {
    public RoomParticipantListPanel(ClientFrame clientFrame,String msg,String roomTitle) {

        setBackground(Color.WHITE);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("참가자");
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(20.0f)); // 현재 폰트를 유지하며 크기만 변경
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
        title.setHorizontalAlignment(JLabel.CENTER);// 수평 가운데 정렬
        title.setVerticalAlignment(JLabel.CENTER);// 수직 가운데 정렬
        add(title);

        String[] parts = msg.split(":"); // ":" 기준으로 문자열을 나눕니다.
        String[] participants = parts[1].split(","); // 참가자 이름을 "," 기준으로 나누어 배열로 만듭니다.
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 40));
            label.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
            label.setHorizontalAlignment(JLabel.CENTER);// 수평 가운데 정렬
            label.setVerticalAlignment(JLabel.CENTER);// 수직 가운데 정렬
            label.setFont(title.getFont().deriveFont(15.0f)); // 현재 폰트를 유지하며 크기만 변경
            add(label);
        }

//        clientFrame.getClient().sendMessage("400/"+roomTitle);
//        String[] participantList = msg.split(":");
//        clientFrame.getClient().sendMessage("400/"+roomTitle);
//        String owner = clientFrame.getClient().receiveMessage();
//
//        for(int i=0;i<participantList.length;i++){
//            JPanel userPanel = new JPanel();
//            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
//
//            //방장 이름 앞에 방장 표시, 아니면 그냥 표시(모든 클라이언트)
//            if(owner.equals(participantList[i])){
//                JLabel userName = new JLabel("방장 "+participantList[i]);userPanel.add(userName);
//            }else{
//                JLabel userName = new JLabel( participantList[i]);userPanel.add(userName);
//            }

//            //2명 이상일 때 방장한테만 버튼 보이게 함(방장 화면에서만)
//            if (participantList.getParticipantListSize() >= 2 && name.equals(ownerName)) {
//                JButton startButton = new JButton("게임 시작");
//                userPanel.add(startButton);
//                setVisible(true);
//            }
//
//            add(userPanel);
//        }
        setVisible(true);
    }

    public void updateMessage(String msg) {
        removeAll(); // 기존 라벨들을 제거
        JLabel title = new JLabel("참가자");
        title.setForeground(Color.BLACK);
        title.setFont(title.getFont().deriveFont(20.0f)); // 현재 폰트를 유지하며 크기만 변경
        title.setMaximumSize(new Dimension(635, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
        title.setHorizontalAlignment(JLabel.CENTER);     // 수평 가운데 정렬
        title.setVerticalAlignment(JLabel.CENTER);       // 수직 가운데 정렬
        add(title);

        String[] parts = msg.split(":"); // ":" 기준으로 문자열을 나눕니다.
        String[] participants = parts[1].split(","); // 참가자 이름을 "," 기준으로 나누어 배열로 만듭니다.
        for(int i=0;i<participants.length;i++){
            JLabel label = new JLabel(participants[i]);
            label.setForeground(Color.BLACK);
            label.setMaximumSize(new Dimension(635, 30));
            label.setAlignmentX(Component.CENTER_ALIGNMENT); // 가운데 정렬
            label.setHorizontalAlignment(JLabel.CENTER); // 수평 가운데 정렬
            label.setVerticalAlignment(JLabel.CENTER); // 수직 가운데 정렬
            label.setFont(title.getFont().deriveFont(15.0f)); // 현재 폰트를 유지하며 크기만 변경
            add(label);
        }
        revalidate();
        repaint();
    }
}
