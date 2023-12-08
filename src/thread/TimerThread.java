package thread;

import notUse.StartFrame;

import javax.swing.*;

import static notUse.MovePanel.gamePanel;
import static notUse.MovePanel.showRolePanel;

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
                    StartFrame.setPanel(showRolePanel,gamePanel);
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
