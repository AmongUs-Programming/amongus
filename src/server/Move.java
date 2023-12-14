package server;

import java.io.Serializable;

public class Move implements Serializable {
    private static final long serialVersionUID = 2L;
    private int posX;
    private int posY;

    public Move(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
    public int setPosX(int posX) {
        return this.posX=posX;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int setPosY(int posY) {
        return this.posY=posY;
    }

}