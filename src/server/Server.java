package server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends JFrame {
    private int port;
    private ServerSocket socket;
    private Socket client_socket;

    private CopyOnWriteArrayList<Room> roomList = new CopyOnWriteArrayList<>(); //게임 방list
    private CopyOnWriteArrayList<UserThread> users = new CopyOnWriteArrayList<>(); //게임에 접속한 모든 사람들
    private Map<String, GameTherad> gameThList = new HashMap<>(); //각 게임마다의 thread
    private JTextArea textArea;

    public Server(int port) {
        this.port = port;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 10, 500, 298);
        contentPane.add(scrollPane);
        textArea = new JTextArea();
        textArea.setEditable(false);
        scrollPane.setViewportView(textArea);
        JLabel portLabel = new JLabel("port number");
        portLabel.setBounds(13, 318, 87, 25);
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
                    AcceptServer acceptServer = new AcceptServer(socket);
                    acceptServer.start();
                    serverStartBtn.setText("Server Running..");
                    serverStartBtn.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
                    txtPortNumber.setEnabled(false); // 더이상 포트번호 수정 못하게 막는다
                } catch (NumberFormatException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        serverStartBtn.setBounds(12, 356, 300, 35);
        contentPane.add(serverStartBtn);
        setVisible(true);
    }

    //serverFrame message
    public void AppendText(String str) {
        textArea.append(str + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    public void AppendMovingInfo(Move msg) {
        textArea.append("roomId = " + msg.getRoomId() + "\n");
        textArea.append("posX = " + msg.getPosX() + "\n");
        textArea.append("posY = " + msg.getPosY() + "\n");
        textArea.append("characterNum = " + msg.getUserName() + "\n");
        textArea.setCaretPosition(textArea.getText().length());
    }

    //유저 이름 등록 후 접속 시 accept()를 통해 user thread 생성
    class AcceptServer extends Thread {
        ServerSocket socket;

        public AcceptServer(ServerSocket socket) {
            this.socket = socket;
        }

        public ServerSocket getSocket() {
            return socket;
        }

        public CopyOnWriteArrayList getUserList() {
            return users;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    AppendText("Waiting new clients..." + socket.getLocalPort());
                    client_socket = socket.accept();
                    AppendText("new user from" + client_socket);

                    //user 마다 thread 생성
                    UserThread user = new UserThread(client_socket, this);
                    synchronized (users) { // 동기화 블록
                        users.add(user);
                    }
                    user.start();
                    AppendText("현재 인원 :" + users.size());

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    } //AcceptServer end

    class UserThread extends Thread {
        public String userName;
        private String request;

        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private InputStream is;
        private OutputStream os;
        private DataInputStream dis;
        private DataOutputStream dos;
        private Socket client_socket;
        private ServerSocket socket;
        private CopyOnWriteArrayList userList;

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

        String name;
        int role = 0;
        //0-시민
        //1-마피아
        Boolean isAlive = true;

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public Boolean getAlive() {
            return isAlive;
        }

        public void setAlive(Boolean alive) {
            isAlive = alive;
        }

        // 클라이언트에게 메시지 전송
        public void sendMessage(String message) {
            try {
                oos.writeObject(message);
                oos.flush();
                System.out.println("server->client : " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void createRoom(String roomTitle) {
            Room newRoom = new Room(socket, roomTitle);
            roomList.add(newRoom);
//            try {
//                oos.writeUTF("Room '" + roomTitle +"' created successfully");
//                oos.flush();
//            } catch (IOException e) {
//                AppendText("Error informing client about room creation");
//                e.printStackTrace();
//            }
        }

        public void removeRoom(String roomTitle) {
            roomList.remove(roomTitle);
//            try {
//                oos.writeUTF("Room '" + roomTitle +"RoomID:"+roomID+"' delete successfully");
//                oos.flush();
//            } catch (IOException e) {
//                AppendText("Error informing client about room delete");
//                e.printStackTrace();
//            }
        }

        public String getRoomIDs() {
            StringBuilder sb = new StringBuilder();
            if(roomList.size()==0){
                return null;
            }else {
                for (Room room : roomList) {
                    String roomId = room.getRoomID();
                    sb.append(roomId + ",");
                }
                return sb.toString();
            }
        }

        public void broadcastMoveToOtherClients(Move move, UserThread sourceUser) {
            for (UserThread user : users) {
                if (user != sourceUser) { // 원본 클라이언트를 제외한 모든 클라이언트에게 전송
                    try {
                        user.sendMove(move);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // UserThread 클래스 내부에 sendMove 메소드 추가
        public void sendMove(Move move) throws IOException {
            oos.writeObject(move);
            oos.flush();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object input = ois.readObject();
                    if (input instanceof Move) {
                        Move move = (Move) input;
                        // Move 객체를 서버의 메인 클래스에 전달하여 다른 클라이언트에게 전송
                        broadcastMoveToOtherClients(move, this);
                        AppendMovingInfo(move);
                    }
                    if(input instanceof String){
                        request = (String) input;
                        System.out.println("client->server requset: " + request);
                        String code = request.split("/")[0];
                        String msg = request.split("/")[1];

                        switch (code) {
                            case "200": //login
                                System.out.println("server login 성공 id : " + msg);
                                this.userName = msg;
                                handlePlayerRegistration(this);
                                this.sendMessage("100/ok");
                                break;
                            case "201": //logout
                                break;
                            case "202": //user Name
                                this.sendMessage("100/"+this.userName);
                                //System.out.println("내이름 : "+userName);
                                break;
                            case "300": //createRoom
                                createRoom(msg);
                                //create gameThread
                                gameThList.put(msg, new GameTherad());
                                //owner enter room
                                gameThList.get(msg).enterRoom(userName, msg, this);
                                //get all paricipants
                                gameThList.get(msg).getParticipant(msg);
                                System.out.println("server Room 생성됌 ID: " + msg);

                                int size1 = gameThList.get(msg).getParticipantNum(msg);
                                //모든 참가자들 이름과 참가자 수 전송하기
                                StringBuilder sb1 = new StringBuilder();
                                sb1.append(size1 + ":");
                                sb1.append(gameThList.get(msg).getParticipantsName(msg));
                                String dataToSend1 = sb1.toString();
                                System.out.println("현재 방 참가자 수: " + dataToSend1);
                                this.sendMessage("100/" + dataToSend1);
                                break;
                            case "301"://removeRoom
                                removeRoom(msg);
                                gameThList.remove(msg);
                                break;
                            case "302"://enterRoom
                                //System.out.println("여기1" + msg);
                                gameThList.get(msg).enterRoom(userName, msg, this);
                                break;
                            case "303": //roomList
                                System.out.println("roomIDS"+getRoomIDs());
                                if(getRoomIDs()==null){
                                    this.sendMessage("100/null");
                                }else{
                                    this.sendMessage("100/" + getRoomIDs());
                                    AppendText("현재 방 list:" + getRoomIDs());
                                }
                                break;
                            case "400"://search owner
                                System.out.println("수신완료");
                                String owner = gameThList.get(msg).setGetRoomOwner(msg);
                                this.sendMessage("100/" + owner);
                                break;
                            case "401"://participantList
                                //print current 참가자 수
                                int size = gameThList.get(msg).getParticipantNum(msg);
                                //모든 참가자들 이름과 참가자 수 전송하기
                                 StringBuilder sb = new StringBuilder();
                                sb.append(size + ":");
                                sb.append(gameThList.get(msg).getParticipantsName(msg));
                                String dataToSend = sb.toString();
                                System.out.println("현재 방 참가자 수: " + dataToSend);
                                Map<String, UserThread> participantList = gameThList.get(msg).getParticipant(msg);
                                for (UserThread userThread : participantList.values()) {
                                    System.out.println("data : " + dataToSend + "send to " + userThread.userName);
                                    userThread.sendMessage("100/" + dataToSend);
                                }
                            case "500"://request game start
                                System.out.println("500 check");
//                                gameThList.get(msg).getUserNamesWithColors(msg);
                                Map<String, UserThread> participantListForChange = gameThList.get(msg).getParticipant(msg);
                                for (UserThread userThread : participantListForChange.values()) {
                                    System.out.println("CHANGEPANEL : " + "send to " + userThread.userName);
                                    userThread.sendMessage("100/" + "CHANGEPANEL");
                                }

                                break;
                            case "501"://게임패널변경
//                                System.out.println("패널변경요청");
//                                Map<String, UserThread> panelParticipantList = gameThList.get(msg).getParticipant(msg);
//                                for(UserThread userThread : panelParticipantList.values()){
//                                    System.out.println("panel : "+"send to"+userThread.userName);
//                                    userThread.sendMessage("100/"+"ChangePanel");
//                                }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


        }
    }

    public void handlePlayerRegistration(UserThread userThread) {
        String userName = userThread.userName;
        userThread.sendMessage("SUCCESS: " + userName + "님, 등록되었습니다.");
        AppendText("새로운 플레이어 등록: " + userName);
    }

    //게임 thread
    class GameTherad extends Thread {
        //        public Participant participant;
        //방 입장
        public void enterRoom(String userName, String roomID, UserThread userThread) {
            Room room = null;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    break;
                }
            }

            if (room != null) {
                // 여기서 participant 객체를 생성합니다.
//                this.participant = new Participant(userName);
                room.enterRoomParticipant(userName, userThread);
                AppendText(room.getRoomID() + ": " + "새로운 참가자 " + userName + " 입장.");
            } else {
                AppendText("Room not found with title: " + roomID);
            }
        }

        public String getParticipantsName(String roomID) { // room에 입장한 플레이어 이름
            StringBuilder sb = new StringBuilder();
            Room room;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    if (room != null) {
                        for (String key : room.getParticipants().keySet()) {
                            sb.append(key).append(",");
                        }
                        return sb.toString();
                    } else {
                        System.out.println("Room not found with title: " + room);
                    }
                    break;
                }
            }
            return null;
        }

        public int getParticipantNum(String roomID) { // room에 입장한 플레이어 수
            Room room;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    if (room != null) {
                        return room.getParticipants().size();
                    } else {
                        System.out.println("Room not found with title: " + room);
                    }
                    break;
                }
            }
            return 0;
        }

        //찾고자 하는 참가자의 역할
        public Map<String, UserThread> getParticipant(String roomID) {
            Room room;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    if (room != null) {
                        return room.getParticipants();
                    } else {
                        System.out.println("Room not found with title: " + room);
                    }
                    break;
                }
            }
            return null;
        }

        //방장 select 및 return
        public String setGetRoomOwner(String roomID) {
            System.out.println("지금 받은 방" + roomID);

            Room room;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    if (room != null) {
                        return room.getParticipants().get(0).getName();
                    } else {
                        System.out.println("Room not found with title: " + room);
                    }
                    break;
                }
            }
            return null;
        }

        //각 paricipant 색 정하기 및 이름과 색 반환
        public Map<String, String> getUserNamesWithColors(String roomID) {
            Map<String, UserThread> allParticipants = null;
            Room findRoom;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    findRoom = rooms;
                    if (findRoom != null) {
                        allParticipants = findRoom.getParticipants();
                    } else {
                        System.out.println("Room not found with title: " + findRoom);
                    }
                    break;
                }
            }
            Map<String, String> userNamesWithColors = new HashMap<>();
            int index = 0;

            for (Map.Entry<String, UserThread> entry : allParticipants.entrySet()) {
                UserThread participant = entry.getValue();
                String name = participant.getName();
                List<String> colorList = Arrays.asList("red", "blue", "green", "yellow");
                String color = colorList.get(index % colorList.size());
                Room room;
                for (Room rooms : roomList) {
                    if (rooms.getRoomTitle().equals(roomID)) {
                        room = rooms;
                        if (room != null) {
                            room.assignColorToPlayer(name, color);
                        } else {
                            System.out.println("Room not found with title: " + room);
                        }
                        break;
                    }
                }
                userNamesWithColors.put(name, color);

                index++;
            }

            return userNamesWithColors;
        }

    }

    public static void main(String[] args) {
        new Server(29998);
    }

}




