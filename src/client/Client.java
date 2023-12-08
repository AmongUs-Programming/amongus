package client;

import java.io.*;
import java.net.*;

public class Client{
    private String serverAddress;
    private int port;
    private Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;

    public Client(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
        connectServer();
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
            if (oos != null) { // null 체크 추가
                oos.writeUTF(message);
                oos.flush();
            } else {
                System.err.println("ObjectOutputStream is null. Connection may be closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMessage() {
        try {
            if (ois != null) { // null 체크 추가
                return ois.readUTF();
            } else {
                System.err.println("ObjectInputStream is null. Connection may be closed.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
