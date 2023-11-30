package frame;

import frame.StartFrame;
import thread.JavaChatServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new StartFrame();
        JavaChatServer chatServer = new JavaChatServer();
        chatServer.startServer();
    }
}