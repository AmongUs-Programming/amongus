package panel;

import User.UserInfo;
import participant.Participant;
import participant.ParticipantList;
import room.RoomList;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;

public class RoomParticipantListPanel extends JPanel {
    public static ParticipantList participantList;
    public static UserInfo userInfo = new UserInfo(UserInfo.getName());

    public RoomParticipantListPanel(int roomID) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        participantList = RoomList.getParticipantList(roomID); // 참가자 목록

        System.out.println("participantList" + participantList);
        System.out.println(participantList.getOwner());
        Map<String, Participant> participantsMap = participantList.getParticipants();
        Set<String> participantKeys = participantsMap.keySet();

        String ownerName = participantList.getOwner(); // 방장 이름 가져오기

        for (String key : participantKeys) {
            Participant participant = participantsMap.get(key);
            String name = participant.getName();

            JPanel userPanel = new JPanel();
            userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));

            //방장 이름 앞에 방장 표시, 아니면 그냥 표시(모든 클라이언트)
            if(ownerName.equals(name)){JLabel userName = new JLabel("방장 "+name);userPanel.add(userName);}
            else{JLabel userName = new JLabel( name);userPanel.add(userName);}



            //2명 이상일 때 방장한테만 버튼 보이게 함(방장 화면에서만)
            if (participantList.getParticipantListSize() >= 2 && name.equals(ownerName)) {
                JButton startButton = new JButton("게임 시작");
                userPanel.add(startButton);
                setVisible(true);
            }

            add(userPanel);
        }
    }
}
