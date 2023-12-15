package client;

import server.Move;
import thread.JavaChatServer;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Client{
    private String serverAddress;
    private int port;
    private Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private String serverMessage;
    private HashMap serverMoveMessage;
    private String serverRealMessage;
    private String name;
    private int score = 0;

    public Client(String serverAddress, int port,String name){
        this.name = name;
        this.serverAddress = serverAddress;
        this.port = port;
        connectServer();
    }

    public String getName(){
        return name;
    }

    public void connectServer(){
        try{
            socket = new Socket(serverAddress,port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        }catch (ConnectException e) {
            System.err.println("Connection refused. Please make sure the server is running.");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            if (oos != null && !socket.isClosed()) { // null 체크 추가
                oos.writeObject(message);
                oos.flush();
            } else {
                System.err.println("ObjectOutputStream is null. Connection may be closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessage() {
        try {
            if (ois != null) { // null 체크 추가
                Object input = ois.readObject();
               if (input instanceof String) {
                    serverMessage =(String)input;
                    System.out.println("client serverMessage:"+serverMessage);
                    if(serverMessage.split("/")[0].equals("100")){
                        serverRealMessage = serverMessage.split("/")[1];
                    }
                }
            } else {
                System.err.println("ObjectInputStream is null. Connection may be closed.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Move> getServerMoveMessage(){
        return serverMoveMessage;
    }
    public String getServerRealMessage(){
        return serverRealMessage;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public static void main(String[] args) throws IOException {
        try{
            new ClientFrame();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//        JavaChatServer chatServer = new JavaChatServer();
//        chatServer.startServer();
    }
}
