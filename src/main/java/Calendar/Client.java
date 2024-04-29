package Calendar;

import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client extends JFrame {
    public static void main(String[] args) {
        var Application = new Client();
    }

    private JPanel app_panel;
    private JTextField host_tf;
    private JTextField port_tf;
    private JTextField calendar_tf;
    private JButton send_btn;

    Client() {
        super();
        setContentPane(app_panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        send_btn.addActionListener(e -> {
            byte[] buffer = new byte[1024];

            try (DatagramSocket socket = new DatagramSocket()) {
                DatagramPacket packet = new DatagramPacket(new byte[1], 1, InetAddress.getByName(host_tf.getText()), Integer.parseInt(port_tf.getText()));
                socket.send(packet);
                //
                packet = new DatagramPacket(buffer, buffer.length); // for receive
                socket.receive(packet);
                calendar_tf.setText(new String(buffer, 0, packet.getLength(), StandardCharsets.UTF_8));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
