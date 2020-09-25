package ja.server.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

public class ServerInfoResponse {
    private Map<String, String> serverConfig;
    private List<ServerInfoResponsePlayer> players;

    public class ServerInfoResponsePlayer {
        private int score;
        private int id;
        private int ping;
        private String name;
        private String clearName;

        public ServerInfoResponsePlayer(int score, int id, int ping, String name, String clearName) {
            this.score = score;
            this.ping = ping;
            this.name = name;
            this.clearName = clearName;
            this.id = id;
        }

        public int getScore() {
            return score;
        }

        public int getPing() {
            return ping;
        }

        public String getName() {
            return name;
        }

        public String getClearName() {
            return clearName;
        }

        public int getId() { return id; }
    }

    public Map<String, String> getServerConfig() {
        return serverConfig;
    }

    public List<ServerInfoResponsePlayer> getPlayers() {
        return players;
    }

    public ServerInfoResponse(InetAddress host, int port) {
        parseResponse(host, port);
    }

    private String getResponse(InetAddress host, int port) {
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

    private void parseResponse(InetAddress host, int port) {
        String response = getResponse(host, port);
        if (response == null || response.length() <= 0) return;
        String[] data = response.split("\n");
        if (data == null || data.length < 2) return;
        String[] keyVal = data[1].split("\\\\");
        if (keyVal == null) return;
        List<String> usersList = Arrays.stream(data).skip(2).map(String::new).collect(Collectors.toList());
        List<ServerInfoResponsePlayer> pls = new ArrayList<>();
        for (String userInfo :
                usersList) {
            String[] split = userInfo.split("\\s+");
            String name = userInfo.split("\"")[1].split("\"")[0];
            String clearName = name.replaceAll("\\^[0-8]", "");
            pls.add(new ServerInfoResponsePlayer(0, Integer.parseInt(split[0]), Integer.parseInt(split[1]), name, clearName));
        }
        Map<String, String> config = new HashMap<>();
        for (int i = 1; i < keyVal.length - 1; i += 2) {
            config.put(keyVal[i], keyVal[i + 1]);
        }
        players = pls;
        serverConfig = config;
    }
}
