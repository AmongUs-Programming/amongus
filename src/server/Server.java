package server;

import participant.Participant;
import room.Room;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends JFrame {
   private  static final long serialUID = 1L;
   private int port;
   private ServerSocket socket;
   private Socket client_socket;

   private ArrayList<Room> roomList = new ArrayList<>(); //게임 방list
private JTextArea textArea;
    public Server(int port){
        this.port=port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400,500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12,10,300,298);
        contentPane.add(scrollPane);
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        JLabel portLabel = new JLabel("port number");
        portLabel.setBounds(13,318,87,25);
        contentPane.add(portLabel);

        JTextField txtPortNumber = new JTextField();
        txtPortNumber.setEditable(false);
        txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
        txtPortNumber.setText(Integer.toString(port));
        txtPortNumber.setBounds(112, 318, 199, 26);
        contentPane.add(txtPortNumber);
        txtPortNumber.setColumns(10);

        JButton serverStartBtn = new JButton("Server Start");
        serverStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket = new ServerSocket(port);
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }
                serverStartBtn.setText("Chat Server Running..");
                serverStartBtn.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
                txtPortNumber.setEnabled(false); // 더이상 포트번호 수정 못하게 막는다
            }
        });
        serverStartBtn.setBounds(12,356,300,35);
        contentPane.add(serverStartBtn);
        setVisible(true);
    }
    //serverFrame message
    public void AppendText(String str) {
        textArea.append("사용자로부터 들어온 메세지 : " + str+"\n");
        textArea.append(str + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }
    //유저 이름 등록 후 접속 시 accept()를 통해 user thread 생성
    class AcceptServer extends Thread{
        ServerSocket socket;
        private ArrayList<UserThread> users = new ArrayList<>(); //게임에 접속한 모든 사람들
        public AcceptServer(ServerSocket socket){
            this.socket=socket;
        }
        public ServerSocket getSocket(){
            return socket;
        }
        public ArrayList getUserList(){
            return users;
        }

        @Override
        public void run() {
            while (true){
                try {
                    AppendText("Waiting new clients..."+socket.getLocalPort());
                    client_socket = socket.accept();
                    AppendText("new user from"+client_socket);
                    //user 마다 thread 생성
                    UserThread user = new UserThread(client_socket,this);
                    users.add(user);
                    user.start();
                    AppendText("현재 인원 :"+users.size());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    } //AcceptServer end

    class RoomThread extends Thread{

        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private Socket client_socket;
        private ServerSocket socket;
        public RoomThread(Socket client_socket, AcceptServer acceptServer){
            // 매개변수로 넘어온 자료 저장
            this.client_socket = client_socket;
            this.socket = acceptServer.getSocket();
            try {
                oos = new ObjectOutputStream(client_socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(client_socket.getInputStream());
            } catch (Exception e) {
                AppendText("userService error");
            }
        }

        public void createRoom(String roomTitle){
            try{
                roomTitle = ois.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int roomID = Room.getRoomID();
            Room newRoom = new Room(socket,roomTitle);
            roomList.add(newRoom);
            try {
                oos.writeUTF("Room '" + roomTitle +"RoomID:"+roomID+"' created successfully");
                oos.flush();
            } catch (IOException e) {
                AppendText("Error informing client about room creation");
                e.printStackTrace();
            }
        }
    }

    class UserThread extends Thread {
        private InputStream is;
        private OutputStream os;
        private DataInputStream dis;
        private DataOutputStream dos;

        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        private Socket client_socket;
        private ServerSocket socket;
        private ArrayList userList;
        public String UserStatus;
        public String userName;
        public Participant participant;

        private int roomID;

        public UserThread(Socket client_socket, AcceptServer acceptServer) {
            // 매개변수로 넘어온 자료 저장
            this.client_socket = client_socket;
            this.socket = acceptServer.getSocket();
            this.userList = acceptServer.getUserList();
            try {
                oos = new ObjectOutputStream(client_socket.getOutputStream());
                oos.flush();
                ois = new ObjectInputStream(client_socket.getInputStream());
            } catch (Exception e) {
                AppendText("userService error");
            }
        }

        public void enterRoom(int roomID) {
            Room room = roomList.get(roomID);
            room.enterRoomParticipant(userName,participant);
            room.getUsers().add(this);
            AppendText("새로운 참가자 " + participant.getName() + " 입장.");
        }

        public int getPlayerNum(int roomID) { // room에 입장한 플레이어 수
            return roomList.get(roomID).getParticipants().size();
        }

        public int getParticipantRole(int roomID, String name){ //찾고자 하는 참가자의 역할
            return roomList.get(roomID).getParticipants().get(name).getRole();
        }

        //각 paricipant 색 정하기 및 이름과 색 반환
        public Map<String, String> getUserNamesWithColors(int roomID) {
            Map<String, Participant> allParticipants = roomList.get(roomID).getParticipants();
            Map<String, String> userNamesWithColors = new HashMap<>();

            int index = 0; // Index to track player position
            for (Map.Entry<String, Participant> entry : allParticipants.entrySet()) {
                Participant participant = entry.getValue();
                String name = participant.getName();

                // Assuming you have a list of color names (e.g., "red", "blue", "green", etc.)
                List<String> colorList = Arrays.asList("red", "blue", "green", "yellow"); // Adjust as needed

                // Get color based on the index
                String color = colorList.get(index % colorList.size());

                // Assign color to player in the Room class
                roomList.get(roomID).assignColorToPlayer(name, color);

                // Add player name and color to the result map
                userNamesWithColors.put(name, color);

                index++;
            }

            return userNamesWithColors;
        }

    }

    public static void main(String[] args) {
        new Server(5880);
    }

}




