package panel;

import client.Client;
import client.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    private Image backgroundImage;
    private Image cafeteriaImage;
    private Image userImage;
    private Image medicalImage;
    private Image shieldImage;
    private Image cctvImage;

    private int userX = 600; // 사용자 이미지 초기 x 좌표
    private int userY = 200; // 사용자 이미지 초기 y 좌표
    private final int userWidth = 20;
    private final int userHeight = 20;

    private JLabel killLabel;
    private JLabel citizenLabel;

    private ClientFrame clientFrame;
    private Map<String, Point> playerPositions = new HashMap<>();
    private Map<String, Image> playerImages = new HashMap<>();
    private String initMessage;
    private String[] color = {"red", "blue", "green", "pink", "purple", "red", "yellow"};
    private String itemLocatioonMsg;

    // 좌표가 맵 위나 통로에 있는지 확인하고 true, false 값 return
    private boolean isValidMode(int x, int y) {
        // 구내식당
        int[] cafeteriaxPoints = {470, 740, 810, 810, 760, 480, 430, 430};
        int[] cafeteriayPoints = {0, 0, 70, 240, 290, 290, 240, 35};
        Polygon cafeteria = new Polygon(cafeteriaxPoints, cafeteriayPoints, 8);
        //Rectangle cafeteria = new Rectangle((getWidth() - 400) / 2,0,390,290);

        // 의무실
        int[] medicalxPoints = {290, 290, 210, 210, 20, 15, 50};
        int[] medicalyPoints = {570, 560, 470, 340, 340, 520, 580};
        Polygon medical = new Polygon(medicalxPoints, medicalyPoints, 7);
        //Rectangle medical = new Rectangle(10,300,290,300-userHeight);
        // cctv
        int[] cctvxPoints = {540, 490, 490, 560, 870, 870};
        int[] cctvyPoints = {340, 390, 490, 560, 560, 340};
        Polygon cctv = new Polygon(cctvxPoints, cctvyPoints, 6);
        //Rectangle cctv = new Rectangle(400,330,490,245-userHeight/2);
        // 무기고
        int[] shieldxPoints = {1230, 1230, 1090, 960, 960, 1000};
        int[] shieldyPoints = {300, 460, 580, 580, 360, 310};
        Polygon shield = new Polygon(shieldxPoints, shieldyPoints, 6);
        //Rectangle shield = new Rectangle(950,300,290,290-userHeight);

        // 의무실 통로
        Rectangle cafeToMedi = new Rectangle(80, 110, 350, 50);
        Rectangle cafeToMedi2 = new Rectangle(80, 110, 55, 230);
        // 무기고통로
        Rectangle cafeToShield = new Rectangle(800, 110, 310, 50);
        Rectangle cafeToShield2 = new Rectangle(1070, 110, 55, 200);
        // cctv통로
        Rectangle cafeToCctv = new Rectangle(605, 300 - userHeight, 50, 75);
        Rectangle shieldToCctv = new Rectangle(870, 420, 95, 50);

        //식탁 장애물
        Ellipse2D.Double table1 = new Ellipse2D.Double(570, 110, 80, 60);
        Ellipse2D.Double table2 = new Ellipse2D.Double(480, 200, 85, 60);
        Ellipse2D.Double table3 = new Ellipse2D.Double(650, 200, 85, 60);
        Ellipse2D.Double table4 = new Ellipse2D.Double(480, 20, 85, 60);
        Ellipse2D.Double table5 = new Ellipse2D.Double(650, 18, 85, 60);


        if (table1.contains(x, y) || table2.contains(x, y) || table3.contains(x, y) || table4.contains(x, y) || table5.contains(x, y))
            return false;

        return (cafeteria.contains(x, y) || cafeToMedi.contains(x, y) || cafeToMedi2.contains(x, y) || cafeToShield.contains(x, y)
                || cafeToShield2.contains(x, y) || medical.contains(x, y) || cafeToCctv.contains(x, y) || cctv.contains(x, y)
                || shield.contains(x, y) || shieldToCctv.contains(x, y));

    }

    public GamePanel(ClientFrame clientFrame) {
        String role = clientFrame.getRole();
        this.clientFrame = clientFrame;
        String roomTitle = this.clientFrame.getRoomTitle();

        //Move List 생성
        this.clientFrame.getClient().sendMessage("304/" + roomTitle);
        this.clientFrame.getClient().sendMessage("600/" + roomTitle);

        //itemLocationMsg
        itemLocatioonMsg = this.clientFrame.getItemLocation();
        String[] itemLocationDataArray = itemLocatioonMsg.split(":")[1].split(";");

        for (String itemLocationData : itemLocationDataArray) {
            if (!itemLocationData.isEmpty()) {
                String[] parts = itemLocationData.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                String imageNum = parts[2];
                updateLocationPosition(imageNum, x, y);
                repaint();
            }
        }

        killLabel = new JLabel("Press spacebar to kill");
        killLabel.setForeground(Color.RED); // Set text color
        killLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and size
        if (role.equals("IMPOSTER")) {
            add(killLabel);
        }

        citizenLabel = new JLabel("run!!");
        citizenLabel.setForeground(Color.BLUE); // Set text color
        citizenLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Set font and size
        if (role.equals("CITIZEN")) {
            add(citizenLabel);
        }

        this.clientFrame.getClient().sendMessage("601/" + userX + "," + userY);
        this.clientFrame.getClient().receiveMessage();
        initMessage = this.clientFrame.getClient().getServerRealMessage();
        System.out.println("initMessage : " + initMessage);
        String[] playerDatas = initMessage.split("&");
        int randomColor = (int) (Math.random() % color.length);
        for (String playerData : playerDatas) {
            if (!playerData.isEmpty()) {
                String[] parts = playerData.split(":");
                String playerName = parts[0];
                System.out.println(color[color.length % (++randomColor)]);
                playerImages.put(playerName, new ImageIcon(Client.class.getResource("/images/" + color[color.length % (++randomColor)] + ".png")).getImage());
            }
        }
        backgroundImage = new ImageIcon(Client.class.getResource("/images/gamebg.png")).getImage();
        cafeteriaImage = new ImageIcon(Client.class.getResource("/images/cafeteria.png")).getImage();
        medicalImage = new ImageIcon(Client.class.getResource("/images/medical.png")).getImage();
        shieldImage = new ImageIcon(Client.class.getResource("/images/shield.png")).getImage();
        cctvImage = new ImageIcon(Client.class.getResource("/images/cctv.png")).getImage();

        setFocusable(true);
        requestFocusInWindow();

        // 키 바인딩 설정
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        // 위 방향 키 바인딩
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUp");
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(0, -10);
            }
        });

        // 아래 방향 키 바인딩
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(0, 10);
            }
        });

        // 왼쪽 방향 키 바인딩
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(-10, 0);
            }
        });

        // 오른쪽 방향 키 바인딩
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePlayer(10, 0);
            }
        });

        // 스페이스바 키 바인딩
        if (clientFrame.getRole().equals("IMPOSTER")) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "spaceBar");
            actionMap.put("spaceBar", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("space");
                }
            });
        }
        MoveThread moveThread = new MoveThread(clientFrame);
        moveThread.start();
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 사용자 주변 원 그리기
        int circleX = userX - 50;
        int circleY = userY - 50;
        int circleDiameter = userWidth + 100;

        // 원 안쪽의 영역을 클리핑
        g.setClip(new Ellipse2D.Double(circleX, circleY, circleDiameter, circleDiameter));

        // 배경이미지 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // 구내식당 그리기
        if (cafeteriaImage != null) {
            int imageWidth = 400;
            int imageHeight = 300;
            int x = (getWidth() - imageWidth) / 2;
            int y = 0;

            g.drawImage(cafeteriaImage, x, y, imageWidth, imageHeight, this);

            // 통로 그리기
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GRAY);

            //식당->의무실 통로
            g2d.fillRect(85, 110, 350, 60);
            g2d.fillRect(85, 110, 60, 190);

            //식당->무기고 통로
            g2d.fillRect(835, 110, 300, 60);
            g2d.fillRect(1075, 110, 60, 190);

            //식당->cctv 통로
            g2d.fillRect(610, 300, 60, 30);
            g2d.dispose();
        }

        //의무실 그리기
        if (medicalImage != null) {
            g.drawImage(medicalImage, 10, 300, 300, 300, this);
        }

        //무기고 그리기
        if (shieldImage != null) {
            g.drawImage(shieldImage, 950, 300, 300, 300, this);
            //무기고 통로 그리기
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GRAY);
            // 무기고->cctv 통로
            g2d.fillRect(900, 420, 50, 60);
            g2d.dispose();
        }

        //cctv방 그리기
        if (cctvImage != null) {
            g.drawImage(cctvImage, 400, 330, 500, 250, this);
        }

//            g.drawImage(userImage, userX, userY, userWidth, userHeight, this);
        for (Map.Entry<String, Point> entry : playerPositions.entrySet()) {
            Point position = entry.getValue();
//                System.out.println("positionImage"+entry.getKey()+","+position.x+","+position.y);
            g.drawImage(playerImages.get(entry.getKey()), position.x, position.y, userWidth, userHeight, this);
        }
        // 클리핑 해제
        g.setClip(null);
        repaint();
    }

    public void updatePlayerPosition(String playerName, int x, int y) {
        playerPositions.put(playerName, new Point(x, y));
    }

    private void movePlayer(int deltaX, int deltaY) {
        int nextX = userX + deltaX;
        int nextY = userY + deltaY;

        if (isValidMode(nextX, nextY)) {
            userX = nextX;
            userY = nextY;
            clientFrame.getClient().sendMessage("601/" + userX + "," + userY);
        }
    }


    class MoveThread extends Thread {
        ClientFrame clientFrame;

        public MoveThread(ClientFrame clientFrame) {
            this.clientFrame = clientFrame;
        }

        @Override
        public void run() {
            while (true) {
                this.clientFrame.getClient().receiveMessage();
                String message = this.clientFrame.getClient().getServerRealMessage();

                System.out.println("message" + message);
                String[] playerDataArray = message.split("&");

                for (String playerData : playerDataArray) {
                    if (!playerData.isEmpty()) {
                        String[] parts = playerData.split(":");
                        String playerName = parts[0];
                        String[] coords = parts[1].split(",");
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        System.out.println("Player: " + playerName + ", X: " + x + ", Y: " + y);
                        updatePlayerPosition(playerName, x, y);
                        repaint();
                    }
                }
            }
        }
    }

}
