package ru.kogotag.ja.utils;

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

    public int getId() {
        return id;
    }
}
