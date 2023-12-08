package server;

public class Participant {
    String name;
    int role=0;
    //0-시민
    //1-마피아
    Boolean isAlive=true;
    public Participant(String name){
        this.name=name;
    }

    public String getName() {
        return name;
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
