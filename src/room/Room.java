package room;

import participant.Participant;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room extends Thread{
    private static String roomTitle;
    private Boolean isPlaying = false;
    private ArrayList users;
    private Map<String,Participant> participants;
    private Map<String, String> participantsColorMap;
    private ServerSocket socket;

    public Room(ServerSocket socket,String roomTitle){
        this.socket=socket;
       this.roomTitle = roomTitle;
       users = new ArrayList();
       participants = new HashMap<>();
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public static String getRoomID(){
        return roomTitle;
    }

    public ArrayList getUsers() {
        return users;
    }

    public Map<String,Participant> getParticipants() {
        return participants;
    }

    public void enterRoomParticipant(String userName,Participant participant){
        System.out.println("enterRoom");
        participants.put(userName,participant);
    }

    public void selectImposter(){
        int size = participants.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (String key : participants.keySet()) {
            if (i == item) {
                participants.get(i).setRole(1);
                System.out.print("임포스터 결정 : ");
                System.out.println(participants.get(i).getName()+participants.get(i).getRole());
            }
            i++;
        }
        throw new IllegalArgumentException("Empty map or invalid index");
    }

    public void assignColorToPlayer(String playerName, String color) { //참가자별로 색깔 정하기
        participantsColorMap.put(playerName, color);
    }

    public String getPlayerColor(String playerName) {
        return participantsColorMap.get(playerName);
    }
}
