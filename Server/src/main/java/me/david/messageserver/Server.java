package me.david.messageserver;

import me.david.messageserver.network.NetServer;

public class Server implements Runnable {

    public Server instance;
    public NetServer netserver;

    public static void main(String[] args){
        new Server().run();
    }

    @Override
    public void run() {
        instance = this;
        netserver = new NetServer();
    }
}
