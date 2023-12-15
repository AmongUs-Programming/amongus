package server;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room extends Thread{
    private String roomTitle;
    private Boolean isPlaying = false;
    private ArrayList users;
    private Map<String, Server.UserThread> participants;
    private Map<String, String> participantsColorMap;
    private ServerSocket socket;

    public Room(ServerSocket socket,String roomTitle){
        this.socket=socket;
       this.roomTitle = roomTitle;
       users = new ArrayList();
       participants = new HashMap<>();
       this.participantsColorMap = new HashMap<>();
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public String getRoomID(){
        return roomTitle;
    }


    public Map<String, Server.UserThread> getParticipants() {
        return participants;
    }

    public void enterRoomParticipant(String userName, Server.UserThread userThread){
        System.out.println("enterRoom");
        participants.put(userName,userThread);
    }
    public void assignColorToPlayer(String playerName, String color) {
        this.participantsColorMap.put(playerName, color);
    }



}
