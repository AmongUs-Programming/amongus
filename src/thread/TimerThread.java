package thread;

import client.ClientFrame;

import javax.swing.*;

public class TimerThread extends Thread{
    private ClientFrame clientFrame;
    private JLabel text = null;
    private int count;
    private int delay = 1000;
    public TimerThread(ClientFrame clientFrame,JLabel text, int count){
        this.clientFrame = clientFrame;
        this.text = text;
        this.count = count;
    }
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(delay);
                if(count<=0){
                    //panel 바꾸기
                    clientFrame.setPanelState(ClientFrame.PanelState.GAME_PANEL);  // 상태를 ROOM_LIST_PANEL로 변경
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
