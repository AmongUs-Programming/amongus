package notUse;

import server.Participant;

import java.util.HashMap;
import java.util.Map;


public class ParticipantList {
   private Map<String, Participant> participants;
    public ParticipantList(){
      this.participants = new HashMap();
    }

    public void addParticipant(String name){
        Participant participant = new Participant(name);
        participants.put(name,participant);
        System.out.println("participant 넣기 성공: "+name);
    }
    public int getParticipantListSize(){return participants.size();}

    public Map<String, Participant> getParticipants() {
        return participants;
    }

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

    public void setAlive(String name,Boolean state){
        participants.get(name).setAlive(state);
    }

    public void setRole(String name,int role){
        participants.get(name).setRole(role);
    }


}
