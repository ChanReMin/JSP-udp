package Bai3_ChatRoom;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Server extends JFrame {
    private static final Set<Map.Entry<InetAddress, Integer>> clientSet = new HashSet<>();


    public static void main(String[] args) {
        var Application =  new Server();

        byte[] messsage_buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(messsage_buffer, messsage_buffer.length);
        try (DatagramSocket socket = new DatagramSocket(9999)) {
            while (true) {
                socket.receive(packet);
                clientSet.add(new SimpleEntry<>(packet.getAddress(), packet.getPort()));
                String client_message = new String(packet.getData(), 0, packet.getLength());
                String response = STR."Client (\{packet.getAddress().getHostAddress()}:\{packet.getPort()}): \{client_message}";
                Application.chatroom_ta.append(response + System.lineSeparator());
                // Response the message to the client
                BroadCast(response);
            }
        } catch (SocketException e) {
            System.out.println(STR."SocketException: \{e.getMessage()}");
        } catch (IOException e) {
            System.out.println(STR."IOException\{e.getMessage()}");
        }
    }

    private JTextArea message_ta;
    private JButton send_btn;
    private JTextArea chatroom_ta;
    private JPanel app_panel;


    Server() {
        super();
        super.setContentPane(app_panel);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setSize(500, 500);
        super.pack();

        send_btn.addActionListener(_ -> {

            String message = STR."Server: \{message_ta.getText()}";
            chatroom_ta.append(STR."\{message}\{System.lineSeparator()}");

            // Send the message to all connected clients
            try {
                BroadCast(message);
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    static void BroadCast(String message) throws UnknownHostException {
        System.out.println(STR."Broadcast to all clients: \{clientSet}");
        for (var client: clientSet) {
            InetAddress clientAddress = client.getKey();
            int clientPort = client.getValue();
            System.out.println(STR."Client address: \{clientAddress}");
            System.out.println(STR."Client port: \{clientPort}");
            try (DatagramSocket socket = new DatagramSocket()) {
                byte[] buffer = message.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                socket.send(packet);
            } catch (IOException e) {
                System.out.println(STR."IOException: \{e.getMessage()}");
            }
        }
    }
}