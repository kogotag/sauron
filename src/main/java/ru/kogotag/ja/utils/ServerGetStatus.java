package ru.kogotag.ja.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerGetStatus {
    private static ServerGetStatus serverGetStatus;

    public static ServerGetStatus getServerGetStatus() {
        if (serverGetStatus == null) {
            serverGetStatus = new ServerGetStatus();
        }
        return serverGetStatus;
    }

    public String getResponse(InetAddress host, int port) {
        try {
            String msg = "getstatus";
            byte[] prefix = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
            byte[] msgDecoded = msg.getBytes();
            byte[] buf = ArrayUtils.addAll(prefix, msgDecoded);
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, host, port);
            socket.send(packet);
            buf = new byte[4096];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(
                    packet.getData(), 0, packet.getLength());
            socket.close();
            return received;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
