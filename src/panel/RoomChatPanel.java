package panel;

import thread.ChatClient;
import thread.ChatServer;

import javax.sound.midi.Receiver;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

public class RoomChatPanel extends JPanel{

    public RoomChatPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Y축 방향으로 컴포넌트들을 배치하는 BoxLayout 설정

        JTextField textField = new JTextField(); // 채팅창
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(200, 500));
        JScrollPane scrollPane1 = new JScrollPane(textField);// JScrollPane으로 감싸 스크롤 가능하도록 함
        add(scrollPane1);

        JPanel inputPanel = new JPanel(); // 입력창과 전송 버튼을 담을 패널 생성
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS)); // X축 방향으로 컴포넌트들을 배치하는 BoxLayout 설정


        JTextField input = new JTextField("", 1); // 채팅 입력창
        input.setPreferredSize(new Dimension(200, 10));
        inputPanel.add(input);

        JButton send = new JButton("전송");
        inputPanel.add(send);

        add(inputPanel); // 전체 패널에 입력 패널 추가

        setSize(370, 300);
        setVisible(true);

    }


}