package Bai4_DB;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Vector;

public class Server {
    public static void main(String[] args) {
        byte[] messsage_buffer = new byte[2048];
        DatagramPacket packet = new DatagramPacket(messsage_buffer, messsage_buffer.length);

        try (DatagramSocket socket = new DatagramSocket(9999)) {
            while (true) {
                socket.receive(packet);
                String query = new String(packet.getData(), 0, packet.getLength()).toUpperCase();
                System.out.println(STR."Client \{packet.getAddress()}:\{packet.getPort()} query: \{query}");
                // Response the message to the client

                try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ltm", "root", "");
                     Statement statement = connection.createStatement()) {
                    try {
                        if (query.contains("SELECT")) {
                            ResultSet resultSet = statement.executeQuery(query);
                            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                            int nCols = resultSetMetaData.getColumnCount();
                            Vector<Vector<String>> list = new Vector<>();

                            while (resultSet.next()) {
                                Vector<String> record = new Vector<>();
                                for (var i = 1; i <= nCols; i++)
                                    record.add(resultSet.getObject(i).toString());
                                list.add(record);
                            }
                            String response = list.toString();
                            byte[] buffer = response.getBytes();
                            socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
                        } else {
                            String response = STR."Query executed result: \{statement.executeUpdate(query)}";
                            byte[] buffer = response.getBytes();
                            socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
                        }
                    } catch (SQLException e) {
                        String response = STR."SQLException: \{e.getMessage()}";
                        byte[] buffer = response.getBytes();
                        socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
                    }
                } catch (SQLException e) {
                    String response = STR."SQLException: \{e.getMessage()}";
                    byte[] buffer = response.getBytes();
                    socket.send(new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort()));
                }
            }
        } catch (SocketException e) {
            System.out.println(STR."SocketException: \{e.getMessage()}");
        } catch (IOException e) {
            System.out.println(STR."IOException\{e.getMessage()}");
        }
    }
}
