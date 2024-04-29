package Bai1_XuLyChuoi;

import javax.swing.*;
import java.io.*;
import java.net.*;


public class Client extends JFrame {
    public static void main(String[] args) {
        new Client();
    }

    private JPanel app_panel;
    private JTextField host_tf;
    private JTextField port_tf;
    private JTextArea response_ta;
    private JButton send_btn;
    private JTextField message_tf;

    Client() {
        super();
        setContentPane(app_panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        send_btn.addActionListener(_ -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(1000);
                byte[] buffer = message_tf.getText().getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host_tf.getText()), Integer.parseInt(port_tf.getText()));
                socket.send(packet);
                //
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length); // for receive
                socket.receive(packet);
                response_ta.setText(new String(buffer, 0, packet.getLength()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
