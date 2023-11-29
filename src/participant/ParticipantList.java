package participant;

import room.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ParticipantList {
   private Map<String,Participant> participants;
    public ParticipantList(){
      this.participants = new HashMap();
    }

    public void addParticipant(String name){
        Participant participant = new Participant(name);
        participants.put(name,participant);
        System.out.println("participant 넣기 성공: "+name);
    }
    public int getParticipantListSize(){return participants.size();}
    public String getOwner() {
        String owner = null;
        if (!participants.isEmpty()) {
            Map.Entry<String, Participant> entry = participants.entrySet().iterator().next();
            owner = entry.getKey();
            System.out.println("Owner: " + owner);
        }

        return owner;
    }
    public void removeParticipant(String name){
        participants.remove(name);
    }

    public void setAlive(String name,Boolean boo){
        participants.get(name).setAlive(boo);
    }

    public void setRole(String name,int role){
        participants.get(name).setRole(role);
    }

    public ArrayList<String> readParticipantNames(){
        ArrayList<String> nameList = new ArrayList<>();
        for (Map.Entry<String,Participant> entry : participants.entrySet()){
            String name = entry.getKey();
            nameList.add(name);
        }
        return nameList;
    }

}
