package client;

import panel.StartPanel;

import javax.swing.*;

public class ClientFrame extends JFrame {
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
        setVisible(true);
    }
}
