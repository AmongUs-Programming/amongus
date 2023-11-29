package room;

import participant.ParticipantList;

import java.util.HashMap;
import java.util.Map;

public class RoomList {
    private String roomID;
    private Map<Integer,Room> rooms;
    private Map<Integer, ParticipantList> participantLists;
    public RoomList(){
        this.rooms = new HashMap<>();
        this.participantLists = new HashMap<>();
    }

    public void addRoom(int roomID,String roomTitle){
        Room room = new Room(roomTitle);
        ParticipantList participantList = new ParticipantList();
        rooms.put(roomID,room);
        participantLists.put(roomID,participantList);
    }

    public void removeRoom(int roomID){
        rooms.remove(roomID);
        participantLists.remove(roomID);
    }

    public ParticipantList getParticipantList(int roomID){
        return participantLists.get(roomID);
    }
}
