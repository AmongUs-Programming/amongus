package thread;

import javax.swing.*;

public class TimerThread extends Thread{
    private JLabel text = null;
    private int count;
    private int delay = 1000;
    public TimerThread(JLabel text, int count){
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
//                    ClientFrame.setPanelState(ClientFrame.PanelState.ROOM_LIST_PANEL);  // 상태를 ROOM_LIST_PANEL로 변경
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
