package Bai2_Caculator;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client extends JFrame {

    public static void main(String[] args) {
        new Client();
    }

    private JTextField host_tf;
    private JTextField port_tf;
    private JTextField expression_tf;
    private JTextField result_tf;
    private JButton send_btn;
    private JPanel app_panel;

    Client() {
        super();
        super.setContentPane(app_panel);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setVisible(true);
        super.setSize(500, 500);
        super.pack();

        send_btn.addActionListener(_ -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(1000);
                byte[] buffer = expression_tf.getText().getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(host_tf.getText()), Integer.parseInt(port_tf.getText()));
                socket.send(packet);
                //
                buffer = new byte[1024];
                packet = new DatagramPacket(buffer, buffer.length); // for receive
                socket.receive(packet);
                result_tf.setText(new String(buffer, 0, packet.getLength()));
            } catch (IOException e) {
                System.out.println(STR."IOException: \{e.getMessage()}");
            }
        });
    }
}
