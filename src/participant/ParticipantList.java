package participant;

import User.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


public class ParticipantList {
   private Map<String,Participant> participants;
    public ParticipantList(){
      this.participants = new HashMap();
    }

    public void addParticipant(String name){
        Participant participant = new Participant(name);
        participants.put(name,participant);
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

}
