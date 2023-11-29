package participant;

public class Participant {
    String name;
    int role;
    //0-시민
    //1-마피아
    Boolean isAlive;
    public Participant(String name){
        this.name=name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }
}
