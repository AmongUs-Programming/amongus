package panel;

import User.UserInfo;
import client.ClientFrame;
import server.Participant;
import notUse.ParticipantList;
import notUse.RoomList;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class RoomParticipantListPanel extends JPanel {
    public RoomParticipantListPanel(ClientFrame clientFrame,String msg) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel(msg+"-현재 참가자 이름"));
        String[] participantList = msg.split(":");
        clientFrame.getClient().sendMessage("400/owner");
        String owner = clientFrame.getClient().receiveMessage();

        for(int i=0;i<participantList.length;i++){
            JPanel userPanel = new JPanel();
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));

            //방장 이름 앞에 방장 표시, 아니면 그냥 표시(모든 클라이언트)
            if(owner.equals(participantList[i])){
                JLabel userName = new JLabel("방장 "+participantList[i]);userPanel.add(userName);
            }else{
                JLabel userName = new JLabel( participantList[i]);userPanel.add(userName);
            }

//            //2명 이상일 때 방장한테만 버튼 보이게 함(방장 화면에서만)
//            if (participantList.getParticipantListSize() >= 2 && name.equals(ownerName)) {
//                JButton startButton = new JButton("게임 시작");
//                userPanel.add(startButton);
//                setVisible(true);
//            }

            add(userPanel);
        }
    }
}
