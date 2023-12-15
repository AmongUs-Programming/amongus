package panel;

import client.ClientFrame;
import thread.TimerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class SelectItemPanel extends JPanel {
    private Vector<String> gameItem = new Vector<>();
    private ArrayList<String> clickItem = new ArrayList<>();
    private int score = 0;
    private JTextArea textArea;
    private ClientFrame clientFrame;
    private String[] itemNames = {
            "사과", "바나나", "치즈", "초콜릿", "모래시계",
            "동전", "달걀", "금트로피", "레모네이드", "푸딩",
            "호박", "은트로피", "별"
    };

    public SelectItemPanel(ClientFrame clientFrame) {
        this.clientFrame=clientFrame;
        setLayout(new BorderLayout());
        String[] msg = clientFrame.getItemLocation().split(":")[1].split(";");
        int i = 0;
        for (String itemData : msg) {
            String[] parts = itemData.split(",");
            gameItem.add(parts[2]);
            i++;
        }
        JPanel panel = new JPanel();

        for (int j = 0; j < itemNames.length; j++) {
            JLabel label = new JLabel(j+1+" "+itemNames[j]);
            final int index = j + 1;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        System.out.println("doubleclick");
                        // 더블 클릭 시 removeClickItem 호출
                        removeClickItem(String.valueOf(index));
                    } else {
                        // 단일 클릭 시 addClickItem 호출
                        System.out.println("click");
                        addClickItem(String.valueOf(index));
                    }
                }
            });
            panel.add(label);
        }
        add(panel,BorderLayout.CENTER);
        textArea = new JTextArea();
        textArea.append("고른것 : ");
        add(textArea,BorderLayout.SOUTH);

        JLabel timer = new JLabel("");
        new TimerThread(clientFrame,timer,10,"SELECT_ITEM_PANEL").start();
        setVisible(true);
    }

    public void addClickItem(String item) {
        if (!clickItem.contains(item)) {
            clickItem.add(item);
            updateTextArea();
        }
    }

    public void removeClickItem(String item) {
        if (clickItem.contains(item)) {
            clickItem.remove(item);
            updateTextArea();
        }
    }

    private void updateTextArea() {
        textArea.setText("고른것 : "); // textArea 초기화
        for (String item : clickItem) {
            textArea.append(item + "\n"); // 각 요소 추가
        }
        calculScore();
    }

    public void calculScore() {
        for (String item : clickItem) {
            if (gameItem.contains(item)) {
                score += 10;
                clientFrame.getClient().addScore(score);
                System.out.println(score);
            } else {
                score -= 10;
                clientFrame.getClient().minusScore(score);
                System.out.println(score);
            }
        }
    }
}
