import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService pool; // 클라이언트 처리를 위한 쓰레드 풀
    private Map<String, Object> gameData; // 게임 데이터를 저장하는 구조

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(4); // 필요에 따라 쓰레드 수 조정
        gameData = new ConcurrentHashMap<>(); // 동시성 제어가 가능한 Map 구현체
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 대기
                pool.execute(new ClientHandler(clientSocket)); // 새로운 클라이언트 처리를 위한 쓰레드 할당
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream input;
        private ObjectOutputStream output;

        public ClientHandler(Socket socket) throws IOException {
            this.clientSocket = socket;
            this.input = new ObjectInputStream(clientSocket.getInputStream());
            this.output = new ObjectOutputStream(clientSocket.getOutputStream());
        }

        public void run() {
            try {
                while (true) {
                    Object request = input.readObject(); // 클라이언트로부터 데이터 수신
                    // 요청 처리 로직 구현
                    // 요청 타입을 확인하여 처리하는 예시 로직
                    if (request instanceof String) {
                        String command = (String) request;
                        switch (command) {
                            case "CREATE_USER":
                                
                            case "CREATE_ROOM":
                                // 방 생성 로직
                                Room newRoom = new Room(); // 가정한 생성자
                                roomList.addRoom(newRoom);
                                sendResponse("ROOM_CREATED");
                                break;
                            case "JOIN_ROOM":
                                // 방에 참가하는 로직
                                // 추가 정보 필요: 어느 방에 참가할지, 참가자 정보 등
                                break;
                            case "LEAVE_ROOM":
                                // 방에서 나가는 로직
                                // 추가 정보 필요: 어느 방에서 나갈지, 참가자 정보 등
                                break;

                            // 기타 다른 명령어 처리
                            default:
                                sendResponse("UNKNOWN_COMMAND");
                                break;
                        }
                    }
                    output.writeObject(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    input.close();
                    output.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sendResponse(Object response) {
        // 클라이언트에게 응답을 보내는 메소드
        try {
            output.writeObject(response);
            output.flush();
        } catch (IOException e) {
            System.out.println("Error sending response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 12345; // 포트 번호 설정
        try {
            Server server = new Server(port);
            server.start(); // 서버 시작
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
