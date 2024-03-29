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
                    if(panelName.equals("LOADING_PANEL")){
                        clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);
                    }else if(panelName.equals("GAME_PANEL")){
                        clientFrame.setPanelState(ClientFrame.PanelState.SELECT_ITEM_PANEL);
                    }else if(panelName.equals("SELECT_ITEM_PANEL")){
                        clientFrame.setPanelState(ClientFrame.PanelState.GAME_OVER_PANEL);
                    }else{
                        clientFrame.getClient().sendMessage("305/"+clientFrame.getRoomTitle());
                        clientFrame.setPanelState(ClientFrame.PanelState.START_PANEL);
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
