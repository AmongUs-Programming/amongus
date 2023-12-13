package client;

import java.io.*;
import java.net.*;

public class Client{
    private String serverAddress;
    private int port;
    private Socket socket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private String serverMessage;
    private Object serverMoveMessage;
    private String name;

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

    public void sendMoveMessage(Object message) {
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

    public String receiveMoveMessage() {
        try {
            if (ois != null) { // null 체크 추가
                serverMoveMessage = ois.readUTF();
            } else {
                System.err.println("ObjectInputStream is null. Connection may be closed.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public String receiveMessage() {
        try {
            if (ois != null) { // null 체크 추가
                serverMessage = ois.readUTF();
                System.out.println("client serverMessage:"+serverMessage);
                if(serverMessage.split("/")[0].equals("100")){
                    return serverMessage.split("/")[1];
                }
            } else {
                System.err.println("ObjectInputStream is null. Connection may be closed.");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

}
