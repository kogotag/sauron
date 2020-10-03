package ru.kogotag.ja.utils;

import ru.kogotag.bot.Config;

import java.util.List;

public class ServerMonitoring implements Runnable{
    private String response;
    private Server server;
    private List<String> playerNames;
    private ServerInfoResponse serverInfoResponse;
    private int loopTime = 60000;

    public ServerMonitoring(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        while (true){
            try {
                doGetResponse();
                doGetServerInfoResponse();
                doGetPlayerNames();
                Thread.sleep(loopTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doGetResponse(){
        response = ServerGetStatus.getServerGetStatus().getResponse(server.getHost(), server.getPort());
    }

    private void setLoopTime(){
        try{
            loopTime = Integer.parseInt(Config.getConfig().getSingleParameter("monitoring_timer_loop_time"));
        } catch (Exception e){}
    }

    private void doGetPlayerNames(){
        playerNames = serverInfoResponse.getPlayersClearNames();
    }

    private void doGetServerInfoResponse(){
        serverInfoResponse = new ServerInfoResponse(response);
    }
}
