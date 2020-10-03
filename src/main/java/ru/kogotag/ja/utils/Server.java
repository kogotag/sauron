package ru.kogotag.ja.utils;

import java.net.InetAddress;

public class Server {
    private InetAddress host;
    private int port;

    public Server(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public InetAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
