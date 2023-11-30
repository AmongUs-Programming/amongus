package role;

import participant.Participant;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Role {
    private Vector<Participant> ciritizen;
    private Participant mapia;
    public Role(Map<String, Participant> participantMap){
        String random = getRandomKey(participantMap);
        mapia = participantMap.get(random);
        mapia.setRole(1);
        System.out.println("선택된 마피아 : "+participantMap.get(random).getName());
        System.out.println("마피아는"+mapia.getName());

        //시민인 경우 vector에 저장
        // 시민인 경우 vector에 저장
        ciritizen = new Vector<>();
        for (Map.Entry<String, Participant> entry : participantMap.entrySet()) {
            Participant participant = entry.getValue();
            if (participant != mapia) {
                ciritizen.add(participant);
            }
        }

        // ciritizen에 있는 모든 참가자의 이름 출력
        for (Participant citizen : ciritizen) {
            System.out.println("시민: " + citizen.getName());
        }
    }

    public Vector<Participant> getCiritizen() {
        return ciritizen;
    }

    public Participant getMapia() {
        return mapia;
    }

    // HashMap에서 무작위로 키 선택하는 함수수
    private static <K, V> K getRandomKey(Map<String, Participant> map) {
        int size = map.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (String key : map.keySet()) {
            if (i == item) {
                return (K) key;
            }
            i++;
        }
        throw new IllegalArgumentException("Empty map or invalid index");
    }
}
