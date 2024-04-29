package Calendar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        byte[] messsage_buffer = new byte[1024];
        byte[] response_buffer;
        String response;
        DatagramPacket packet;
        try (DatagramSocket socket = new DatagramSocket(9999)) {
            while (true) {
                // Receive the message from the client
                packet = new DatagramPacket(messsage_buffer, messsage_buffer.length);
                socket.receive(packet);
                System.out.println("Received client input: " + new String(messsage_buffer, 0, packet.getLength(), StandardCharsets.UTF_8));
                // Response the message to the client
                response = new Date().toString();
                response_buffer = response.getBytes();
                packet = new DatagramPacket(response_buffer, response_buffer.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
            }
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }
    }
}
