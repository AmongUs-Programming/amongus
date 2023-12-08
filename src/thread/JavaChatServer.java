package thread;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class JavaChatServer {

    private ServerSocket socket;
    private Socket client_socket;
    private Vector<UserService> UserVec = new Vector<>();

    public static void main(String[] args) {
        JavaChatServer chatServer = new JavaChatServer();
        chatServer.startServer();
    }

    public JavaChatServer() {
        try {
            socket = new ServerSocket(30000);
        } catch (NumberFormatException | IOException e1) {
            e1.printStackTrace();
        }
        System.out.println("Chat Server Running..");
        AcceptServer accept_server = new AcceptServer();
        accept_server.start();
    }

    class AcceptServer extends Thread {
        @SuppressWarnings("unchecked")
        public void run() {
            while (true) {
                try {
                    System.out.println("Waiting Chat clients ...");
                    client_socket = socket.accept();
                    System.out.println("Chat New client: " + client_socket);
                    UserService new_user = new UserService(client_socket);
                    UserVec.add(new_user);
                    System.out.println("Chat User joined. Current users: " + UserVec.size());
                    new_user.start();
                } catch (IOException e) {
                    System.out.println("Chat Accept error...");
                }
            }
        }
    }

    class UserService extends Thread {
        private InputStream is;
        private OutputStream os;
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client_socket;
        private Vector<UserService> user_vc;
        private String UserName = "";

        public UserService(Socket client_socket) {
            this.client_socket = client_socket;
            this.user_vc = UserVec;
            try {
                is = client_socket.getInputStream();
                dis = new DataInputStream(is);
                os = client_socket.getOutputStream();
                dos = new DataOutputStream(os);
                String line1 = dis.readUTF();
                String[] msg = line1.split(" ");
                UserName = msg[1].trim();
                System.out.println("New user: " + UserName);
                WriteOne(UserName + " 님 환영합니다\n");
            } catch (Exception e) {
                System.out.println("userService error");
            }
        }

        public void WriteOne(String msg) {
            try {
                dos.writeUTF(msg);
            } catch (IOException e) {
                System.out.println("dos.write() error");
                try {
                    dos.close();
                    dis.close();
                    client_socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                UserVec.removeElement(this);
                System.out.println("User left. Remaining users: " + UserVec.size());
            }
        }

        public void WriteAll(String str) {
            for (int i = 0; i < user_vc.size(); i++) {
                UserService user = user_vc.get(i);
                user.WriteOne(str);
            }
        }

        public void run() {
            while (true) {
                try {
                    String msg = dis.readUTF();
                    msg = msg.trim();
                    System.out.println(msg);
                    WriteAll(msg + "\n");
                } catch (IOException e) {
                    System.out.println("dis.readUTF() error");
                    try {
                        dos.close();
                        dis.close();
                        client_socket.close();
                        UserVec.removeElement(this);
                        System.out.println("User left. Remaining users: " + UserVec.size());
                        break;
                    } catch (Exception ee) {
                        break;
                    }
                }
            }
        }
    }

    public void startServer() {
        System.out.println("Chat Server started...");
        AcceptServer accept_server = new AcceptServer();
        accept_server.start();
    }
}
