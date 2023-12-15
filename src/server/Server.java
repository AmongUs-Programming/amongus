package server;

import thread.JavaChatServer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends JFrame {
    private List<Item> Items;
    private Random random = new Random();
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
        textArea.append("posX = " + msg.getPosX() + "\n");
        textArea.append("posY = " + msg.getPosY() + "\n");
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
        int role = 0;
        //0-시민
        //1-마피아
        Boolean isAlive = true;

        private String room;
        private String Mafia="";
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
        }

        public void removeRoom(String roomTitle) {
            roomList.remove(roomTitle);
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
                                JavaChatServer chatServer = new JavaChatServer();
                                chatServer.startServer();
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
                                gameThList.get(msg).setOwner(userName);
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
                            case "304":
                                room = msg;
                                break;
                            case "400"://search owner
                                System.out.println("수신완료");
//                                String owner = gameThList.get(msg).setGetRoomOwner(msg);
                                this.sendMessage("100/OWNER:" + gameThList.get(msg).getOwner());
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
                                break;
                            case "500"://request game start
                                System.out.println("500 check");
                                Map<String, UserThread> participantListForChange = gameThList.get(msg).getParticipant(msg);
                                for (UserThread userThread : participantListForChange.values()) {
                                    System.out.println("CHANGEPANEL : " + "send to " + userThread.userName);
                                    userThread.sendMessage("100/" + "CHANGEPANEL");
                                }
                                GameTherad gameThread = gameThList.get(msg);
                                gameThread.selectImposter(msg);
                                break;

                            case "501"://임포스터 여부
                                System.out.println("501 check");
                                gameThList.get(msg).selectImposter(msg);
                                Map<String, UserThread> participant = gameThList.get(msg).getParticipant(msg);
                                for (UserThread userThread : participant.values()) {
                                    System.out.println("SENDROLE : " + "send to " + userThread.userName);
                                    String role = userThread.getRole() == 1 ? "IMPOSTER" : "CITIZEN";
                                    userThread.sendMessage("100/ROLE" + role);
                                    if (role.equals("IMPOSTER")) {
                                        Mafia = userThread.userName; // 마피아 역할일 경우, mafia 변수에 이름 할당
                                    }
                                }
                                break;

                            case "502":
                                System.out.println("색상 지정 요청 받음");
                                String userName="";
                                String color="";
                                Map<String, String> userColors = gameThList.get(msg).getUserNamesWithColors(msg);
                                System.out.println("userColors : "+userColors);
                                for (Map.Entry<String, String> entry : userColors.entrySet()) {
                                    userName = entry.getKey();// 사용자 이름
                                    color = entry.getValue(); // 해당 사용자의 색상

                                    Map<String, UserThread> participantColor = gameThList.get(msg).getParticipant(msg);
                                    for (UserThread userThread : participantColor.values()) {
                                        if(userThread.getName().equals(userName)){
                                        userThread.sendMessage("100/COLOR"+color);
                                        }
                                    }
                                }
                                break;
                            case "503":{
                                String userRole;
                                System.out.println("503 check"+Mafia);
                                Map<String, UserThread> participantListForGameOver = gameThList.get(msg).getParticipant(msg);
                                for (UserThread userThread : participantListForGameOver.values()) {
                                    userThread.sendMessage("100/" + Mafia);
                                }

                                break;
                            }
                            case "504":
                            {
                                System.out.println("504 check");
                                Map<String, UserThread> participantListForItems = gameThList.get(msg).getParticipant(msg);
                                Items = new ArrayList<>();
                                initializeItems();
                                StringBuilder itemsInfo = new StringBuilder("100/ITEMS:");
                                for (Item item : Items) {
                                    itemsInfo.append(item.getX()).append(",").append(item.getY()).append(",").append(item.getItemNum()).append(";");
                                }
                                for (UserThread userThread : participantListForItems.values()) {
                                    userThread.sendMessage(itemsInfo.toString());
                                }
                                break;
                            }



                            case "600": //make Move
                                gameThList.get(msg).makeParticipantMove(msg);
                                break;
                            case "601": //update Move
                                Move myMove =  gameThList.get(room).getParticipantMove(this);
                                //msg - x,y
                                int x = Integer.parseInt(msg.split(",")[0]);
                                int y = Integer.parseInt(msg.split(",")[1]);
                                myMove.setPosX(x);
                                myMove.setPosY(y);
                                gameThList.get(room).updateParticipantMove(this,myMove);
                                Map<String, UserThread> participantListForMove = gameThList.get(room).getParticipant(room);
                                for (UserThread userThread : participantListForMove.values()) {
                                    System.out.println("data : " + gameThList.get(room).getParticipantListMove() + "send to " + userThread.userName);
                                    userThread.sendMessage("100/" + gameThList.get(room).getParticipantListMove());
                                }
                                break;
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

    private void initializeItems() {
        generateRandomItems(675, 1175, 0, 300, 2); // 범위: 675,0~1175,300 (2개의 보물)
        generateRandomItems(10, 310, 300, 600, 1); // 범위: 10,300~310,600 (1개의 보물)
        generateRandomItems(950, 1250, 300, 600, 1); // 범위: 950,300~1250,600 (1개의 보물)
        generateRandomItems(400, 900, 330, 780, 1); // 범위: 400,330~900,780 (1개의 보물)
    }

    private void generateRandomItems(int xStart, int xEnd, int yStart, int yEnd, int numTreasures) {
        List<Integer> itemNumbers = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            itemNumbers.add(i);
        }
        Collections.shuffle(itemNumbers);
        for (int i = 0; i < numTreasures; i++) {
            int x = random.nextInt(xEnd - xStart + 1) + xStart;
            int y = random.nextInt(yEnd - yStart + 1) + yStart;
            int itemNum = itemNumbers.get(i);
            Items.add(new Item(x, y, itemNum));
        }
    }



    public void handlePlayerRegistration(UserThread userThread) {
        String userName = userThread.userName;
        userThread.sendMessage("SUCCESS: " + userName + "님, 등록되었습니다.");
        AppendText("새로운 플레이어 등록: " + userName);
    }

    //게임 thread
    class GameTherad extends Thread {
        private Map<String,Move> participantMove = new HashMap<>();
        private String owner;

        public void setOwner(String owner) {
            this.owner = owner;
        }
        public String getOwner(){
            return owner;
        }

        public void makeParticipantMove(String roomID){
            Room room;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    room = rooms;
                    if (room != null) {
                        for (String key : room.getParticipants().keySet()) {
                            participantMove.put(key,new Move(600,200));
                        }
                    } else {
                        System.out.println("Room not found with title: " + room);
                    }
                    break;
                }
            }
        }
        public void updateParticipantMove(UserThread userThread,Move move){
            participantMove.put(userThread.userName,move);
        }
        public String getParticipantListMove(){
            StringBuilder sb = new StringBuilder();
            for (String key : participantMove.keySet()) {
                sb.append(key).append(":").append(participantMove.get(key).getPosX()).append(",").append(participantMove.get(key).getPosY()).append("&");
            }
            return sb.toString();
        }
        public Move getParticipantMove(UserThread userThread){
            return participantMove.get(userThread.userName);
        }
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

        //역할지정
        public void selectImposter(String roomID) {
            Room room = null;
            for (Room r : roomList) {
                if (r.getRoomTitle().equals(roomID)) {
                    room = r;
                    break;
                }
            }

            if (room == null || room.getParticipants().isEmpty()) {
                throw new IllegalArgumentException("Room not found or no participants in room: " + roomID);
            }

            List<UserThread> participants = new ArrayList<>(room.getParticipants().values());
            int randomIndex = new Random().nextInt(participants.size());
            UserThread imposter = participants.get(randomIndex);
            imposter.setRole(1); // 임포스터로 설정
            System.out.println("임포스터 결정: " + imposter.userName);
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

        //각 paricipant 색 정하기 및 이름과 색 반환

        public Map<String, String> getUserNamesWithColors(String roomID) {
            Room findRoom = null;
            for (Room rooms : roomList) {
                if (rooms.getRoomTitle().equals(roomID)) {
                    findRoom = rooms;
                    break;
                }
            }
            if (findRoom == null) {
                System.out.println("Room not found with title: " + roomID);
                return Collections.emptyMap(); // 룸을 찾지 못한 경우 빈 맵 반환
            }

            System.out.println("Room found : " + findRoom);
            Map<String, UserThread> allParticipants = findRoom.getParticipants(); // 참가자 맵 가져오기

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




