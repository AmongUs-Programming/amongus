package notUse;

import server.Room;

import java.util.HashMap;
import java.util.Map;

public class RoomList {
    private static Map<Integer, Room> rooms;
    private static Map<Integer, ParticipantList> participantLists;
    public RoomList(){
        this.rooms = new HashMap<>();
        this.participantLists = new HashMap<>();
    }

//    public void addRoom(int roomID,String roomTitle){
//        Room room = new Room(roomTitle,false);
//        ParticipantList participantList = new ParticipantList();
//        rooms.put(roomID,room);
//        participantLists.put(roomID,participantList);
//        System.out.println("roomID:"+roomID);
//    }

    public void removeRoom(int roomID){
        rooms.remove(roomID);
        participantLists.remove(roomID);
    }

    public Map<Integer,Room> getRoomList(){
        return rooms;
    }

    public static ParticipantList getParticipantList(int roomID){
        return participantLists.get(roomID);
    }
}
