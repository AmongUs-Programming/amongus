package main;

import client.ClientFrame;
import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try{
            new Server(29998);
            new ClientFrame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        JavaChatServer chatServer = new JavaChatServer();
//        chatServer.startServer();
    }
}