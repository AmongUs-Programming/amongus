package client;

import panel.GamePanel;
import panel.RoomListPanel;
import panel.RoomPanel;
import panel.StartPanel;

import javax.swing.*;

public class ClientFrame extends JFrame {
    private Client client;  // Client 객체를 저장

    // Client 객체를 설정하는 메서드
    public void setClient(Client client) {
        this.client = client;
    }

    // Client 객체에 접근하는 메서드
    public Client getClient() {
        return this.client;
    }

    public enum PanelState {
        START_PANEL, ROOM_LIST_PANEL, ROOM_PANEL, GAME_PANEL, VOTE_PANEL, GAME_OVER_PANEL
    }
    private volatile PanelState currentPanelState = PanelState.START_PANEL;
    private volatile boolean isChange = false;

    public static final int SCREEN_WIDTH = 1270;
    public static final int SCREEN_HEIGHT = 630;

    public ClientFrame(){
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setContentPane(new StartPanel(this));
        ChangePanelThread chth = new ChangePanelThread();
        chth.start();
        setVisible(true);
    }
    class ChangePanelThread extends Thread{
        @Override
        public void run() {
            while (true){
                if(isChange){
                    synchronized (ClientFrame.this) {
                        isChange = false;
                        //화면 변경
                        selectPanel();
                    }
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public synchronized void setPanelState(PanelState newState) {
        this.currentPanelState = newState;
        this.isChange = true;
    }

    private void selectPanel(){
        switch (currentPanelState) {
            case START_PANEL:
                setContentPane(new StartPanel(this));
                break;
            case ROOM_LIST_PANEL:
                setContentPane(new RoomListPanel(this));
                break;
            case ROOM_PANEL:
                setContentPane(new RoomPanel(this));
                break;
            case GAME_PANEL:
                setContentPane(new GamePanel());
                break;
        }
        revalidate();
        repaint();
    }

}
