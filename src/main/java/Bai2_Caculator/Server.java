package Bai2_Caculator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) {
        byte[] messsage_buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(messsage_buffer, messsage_buffer.length);
        try (DatagramSocket socket = new DatagramSocket(9999)) {
            while (true) {
                socket.receive(packet);
                String client_message = new String(packet.getData(), 0, packet.getLength());
                System.out.println(STR."Received client input: \{client_message}");
                // Response the message to the client
                String response = String.valueOf(TinhBieuThuc.eval(client_message));
                byte[] response_buffer = response.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(response_buffer, response_buffer.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket);
            }
        } catch (SocketException e) {
            System.out.println(STR."SocketException: \{e.getMessage()}");
        } catch (IOException e) {
            System.out.println(STR."IOException\{e.getMessage()}");
        }
    }
}
