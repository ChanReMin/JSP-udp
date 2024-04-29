package Bai3_ChatRoom;

import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Client extends JFrame {
    public static void main(String[] args) {
        new Client();
    }

    private JTextField host_tf;
    private JTextField port_tf;
    private JTextArea message_ta;
    private JButton send_btn;
    private JTextArea chatroom_ta;
    private JPanel app_panel;
    private static final DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    Client() {
        super();
        super.setContentPane(app_panel);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setSize(500, 500);
        super.pack();

        // join the chat room
        try {
            String joinMessage = "Join the chat room";
            socket.send(new DatagramPacket(joinMessage.getBytes(), joinMessage.length(), InetAddress.getByName(host_tf.getText()), Integer.parseInt(port_tf.getText())));
        } catch (IOException e) {
            System.out.println(STR."IOException: \{e.getMessage()}");
        }

        new MessageReceiver(socket, chatroom_ta).start();

        send_btn.addActionListener(_ -> {
            try {
                String message = message_ta.getText();
                byte[] buffer = message.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host_tf.getText()), Integer.parseInt(port_tf.getText()));
                socket.send(packet);
            } catch (IOException ex) {
                System.out.println(STR."IOException: \{ex.getMessage()}");
            }
        });
    }

    private static class MessageReceiver extends Thread {
        private final DatagramSocket socket;
        private final JTextArea chatroom_ta;

        public MessageReceiver(DatagramSocket socket, JTextArea chatroom_ta) {
            this.socket = socket;
            this.chatroom_ta = chatroom_ta;
        }

        @Override
        public void run() {
            // Receive message from server
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                while (true) {
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(STR."Received message: \{message}");
                    chatroom_ta.append(STR."\{message}\{System.lineSeparator()}");
                }
            } catch (IOException e) {
                System.out.println(STR."IOException: \{e.getMessage()}");
            }
        }
    }
}
