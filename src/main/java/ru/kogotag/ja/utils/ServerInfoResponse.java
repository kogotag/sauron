package ru.kogotag.ja.utils;

import java.util.*;
import java.util.stream.Collectors;

public class ServerInfoResponse {
    private Map<String, String> serverConfig;
    private List<ServerInfoResponsePlayer> players;

    public Map<String, String> getServerConfig() {
        return serverConfig;
    }

    public List<ServerInfoResponsePlayer> getPlayers() {
        return players;
    }

    public ServerInfoResponse(String response) {
        parseResponse(response);
    }

    public void parseResponse(String response) {
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

    public List<String> getPlayersClearNames(){
        if(players == null || players.size()<=0) return null;
        List<String> names = new ArrayList<>();
        for (ServerInfoResponsePlayer player :
                players) {
            names.add(player.getClearName());
        }
        return names;
    }

    public List<String> getPlayersNames(){
        if(players == null || players.size()<=0) return null;
        List<String> names = new ArrayList<>();
        for (ServerInfoResponsePlayer player :
                players) {
            names.add(player.getName());
        }
        return names;
    }
}
