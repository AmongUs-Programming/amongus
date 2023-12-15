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

    public SelectItemPanel(ClientFrame clientFrame) {
        setLayout(new FlowLayout());
        String[] msg = clientFrame.getItemLocation().split(":")[1].split(";");
        int i = 0;
        for (String itemData : msg) {
            String[] parts = itemData.split(",");
            gameItem.add(parts[2]);
            i++;
        }

        for (int j = 1; j <= 13; j++) {
            JLabel label = new JLabel(String.valueOf(j));
            final int index = j;  // j 값을 final 변수로 복사
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
            add(label);
        }

        textArea = new JTextArea();
        textArea.append("고른것 : ");
        add(textArea);

        JLabel timer = new JLabel("10");
//        new TimerThread(clientFrame,timer,10,"SELECT_ITEM_PANEL");
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
                System.out.println(score);
            } else {
                score -= 10;
                System.out.println(score);
            }
        }
    }
}
