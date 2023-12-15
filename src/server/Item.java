package server;

import java.util.Random;

public class Item {
    private int x;
    private int y;
    private int itemNum;

    public Item(int x, int y, int itemNum) {
        this.x = x;
        this.y = y;
        this.itemNum = itemNum;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getItemNum(){return itemNum;}
}
