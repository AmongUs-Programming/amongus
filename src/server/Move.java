package server;

import java.io.Serializable;

enum State {LEFT, RIGHT, FRONT,BACK}

public class Move implements Serializable {
    private static final long serialVersionUID = 2L;
    private String code;
    private String roomId;
    private int posX;
    private int posY;
    private String userName;
    private State type;

    public Move(String roomId, int posX, int posY, String userName) {
        this.roomId = roomId;
        this.posX = posX;
        this.posY = posY;
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }


    public String getRoomId() {
        return roomId;
    }


    public int getPosX() {
        return posX;
    }


    public int getPosY() {
        return posY;
    }

    public String getUserName() {
        return userName;
    }

    public State getType() {
        return type;
    }

}