package client;

import panel.GamePanel;
import panel.RoomListPanel;
import panel.RoomPanel;
import panel.StartPanel;

import javax.swing.*;

public class ClientFrame extends JFrame {
    public static boolean isChange;
    public static boolean isStartPanel;
    public static boolean isRoomListPanel;
    public static boolean isRoomPanel;
    public static boolean isGamePanel;
    public static boolean isVotePanel;
    public static boolean isGameOverPanel;

    public static final int SCREEN_WIDTH = 1270;
    public static final int SCREEN_HEIGHT = 630;

    public ClientFrame(){
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setContentPane(new StartPanel());
        ChangePanelThread chth = new ChangePanelThread();
        chth.start();
        setVisible(true);
    }
    class ChangePanelThread extends Thread{
        @Override
        public void run() {
            while (true){
                if(isChange){
                    isChange=false;
                    selectPanel();
                    System.out.println("화면 변경");
                }try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void selectPanel(){
        if(isStartPanel){
            setContentPane(new RoomListPanel());
            isStartPanel=false;
        }else if(isRoomListPanel){
            setContentPane(new RoomPanel("s",1));
            isRoomListPanel=false;
        }else if(isRoomPanel) {
            setContentPane(new GamePanel());
            isGamePanel=false;
        } else if (isGamePanel) {

        }
        revalidate();
        repaint();
    }
}
