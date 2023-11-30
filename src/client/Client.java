import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Client(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void startClient() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter command: ");
                String command = scanner.nextLine();

                sendRequest(command);

                Object response = input.readObject();
                System.out.println("Server response: " + response);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendRequest(String command) throws IOException {
        output.writeObject(command);
        output.flush();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 12345);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
