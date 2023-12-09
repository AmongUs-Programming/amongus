package panel;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

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
    private final String name = "세은";

    // 좌표가 맵 위나 통로에 있는지 확인하고 true, false 값 return
    private boolean isValidMode(int x, int y){
        // 구내식당
        Rectangle cafeteria = new Rectangle((getWidth() - 400) / 2,0,390,290);
        // 의무실
        Rectangle medical = new Rectangle(10,300,290,300-userHeight);
        // cctv
        Rectangle cctv = new Rectangle(400,330,490,245-userHeight/2);
        // 무기고
        Rectangle shield = new Rectangle(950,300,290,290-userHeight);

        // 의무실 통로
        Rectangle cafeToMedi = new Rectangle(85,110,350,50);
        Rectangle cafeToMedi2 = new Rectangle(85,110,50,190);
        // 무기고통로
        Rectangle cafeToShield = new Rectangle(800,110,310,50);
        Rectangle cafeToShield2 = new Rectangle(1075,110,50,200);
        // cctv통로
        Rectangle cafeToCctv = new Rectangle(605,300-userHeight,50,50);
        Rectangle shieldToCctv = new Rectangle(880,420,80,50);

        return cafeteria.contains(x, y) || cafeToMedi.contains(x, y) || cafeToMedi2.contains(x, y) || cafeToShield.contains(x, y)
                || cafeToShield2.contains(x, y) || medical.contains(x, y) || cafeToCctv.contains(x, y) || cctv.contains(x, y)
                || shield.contains(x, y) || shieldToCctv.contains(x, y);
    }

    public GamePanel() {
        System.out.println("gamepanel입니다");

            // 이미지 로드
            backgroundImage = new ImageIcon(Main.class.getResource("/images/gamebg.png")).getImage();
            cafeteriaImage = new ImageIcon(Main.class.getResource("/images/cafeteria.png")).getImage();
            userImage = new ImageIcon(Main.class.getResource("/images/red.png")).getImage();
            medicalImage = new ImageIcon(Main.class.getResource("/images/medical.png")).getImage();
            shieldImage = new ImageIcon(Main.class.getResource("/images/shield.png")).getImage();
            cctvImage  = new ImageIcon(Main.class.getResource("/images/cctv.png")).getImage();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int nextX=userX;
                int nextY=userY;

                if (key == KeyEvent.VK_UP) {
                    nextY = userY -10;
                    System.out.println("up");
                } else if (key == KeyEvent.VK_DOWN) {
                    nextY = userY+10;
                } else if (key == KeyEvent.VK_LEFT) {
                    nextX = userX-10;
                } else if (key == KeyEvent.VK_RIGHT) {
                    nextX = userX+10;
                }

                if(isValidMode(nextX,nextY)){
                    userX=nextX;
                    userY=nextY;
                }
            }
        });
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 사용자 그리기
        if (userImage != null) {

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
                g2d.fillRect(610,300,60,30);
                g2d.dispose();
            }

            //의무실 그리기
            if (medicalImage != null) {
                g.drawImage(medicalImage, 10, 300, 300, 300, this);
            }

            //무기고 그리기
            if(shieldImage != null){
                g.drawImage(shieldImage, 950,300,300,300,this);
                //무기고 통로 그리기
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.GRAY);
                // 무기고->cctv 통로
                g2d.fillRect(900, 420, 50, 60);
                g2d.dispose();
            }

            //cctv방 그리기
            if(cctvImage != null){
                g.drawImage(cctvImage, 400,330,500,250,this);
            }

            g.drawImage(userImage, userX, userY, userWidth, userHeight, this);
            // 클리핑 해제
            g.setClip(null);
        }

        repaint();
    }
}
