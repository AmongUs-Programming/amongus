package room;

import participant.ParticipantList;

import java.util.HashMap;
import java.util.Map;

public class Room{
    private String roomTitle;

    public Room(String roomTitle){
       this.roomTitle = roomTitle;
    }

    public String getRoomTitle() {
        return roomTitle;
    }
}
