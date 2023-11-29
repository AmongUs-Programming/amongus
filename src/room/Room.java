package room;

import participant.ParticipantList;

import java.util.HashMap;
import java.util.Map;

public class Room{
    private String roomTitle;
    private Boolean isPlaying;

    public Room(String roomTitle, Boolean isPlaying){
       this.roomTitle = roomTitle;
       this.isPlaying = isPlaying;
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
}
