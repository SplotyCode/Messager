package me.david.messageserver;

import me.david.messagecore.logger.GLogger;
import me.david.messageserver.network.NetServer;

import java.util.logging.Level;

public class Server implements Runnable {

    public static Server instance;
    public NetServer netserver;
    public GLogger logger;

    public static void main(String[] args){
        new Server().run();
    }

    @Override
    public void run() {
        instance = this;
        logger = new GLogger(Level.ALL);
        netserver = new NetServer(8888, false, true, true, 1000*5);
    }
}
