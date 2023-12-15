package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class SelectItemPanel extends JPanel {
    private Vector<String> gameItem = new Vector<>();
    private ArrayList<String> clickItem = new ArrayList<>();
    private int score =0;
    public SelectItemPanel(ClientFrame clientFrame){
       String []msg = clientFrame.getItemLocation().split(":")[1].split(";");
        int i = 0;
        for (String itemData : msg) {
            String[] parts = itemData.split(",");
            gameItem.add(parts[3]);
            i++;
        }
        JLabel timer = new JLabel("10");
//        new TimerThread(clientFrame,timer,10,"SELECT_ITEM_PANEL");
    }
    public void addClickItem(String item){
        clickItem.add(item);
    }
    public void removeClickItem(String item){
        clickItem.remove(item);
    }
    public void calculScore(){
        for (String item : clickItem) {
            if (gameItem.contains(item)) {
                score +=10;
            } else {
                score-=10;
            }
        }
    }
}
