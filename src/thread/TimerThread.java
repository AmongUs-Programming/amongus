package thread;

import client.ClientFrame;

import javax.swing.*;

public class TimerThread extends Thread{
    private ClientFrame clientFrame;
    private JLabel text = null;
    private int count;
    private int delay = 1000;
    private String panelName;
    public TimerThread(ClientFrame clientFrame,JLabel text, int count,String panelName){
        this.panelName = panelName;
        this.clientFrame = clientFrame;
        this.text = text;
        this.count = count;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(delay);
                if(count<=-1){
                    //panel 바꾸기
                    if(panelName.equals("SHOW_ROLE_PANEL")){
                        clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
                    }else{
                        clientFrame.setPanelState(ClientFrame.PanelState.ROOM_LIST_PANEL);
                    }
                    this.interrupt();
                    break;
                }else {
                    count--;
                    System.out.println("timer: "+count);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
