package me.david.messageserver;

import me.david.messagecore.logger.GLogger;
import me.david.messageserver.database.AsyncDataBaseConnection;
import me.david.messageserver.database.DataBaseConnection;
import me.david.messageserver.network.NetServer;
import me.david.messageserver.sendqueue.SendQueue;

import java.util.logging.Level;

public class Server implements Runnable {

    public static Server instance;
    public NetServer netserver;
    public SendQueue sendqueue;
    public GLogger logger;
    public DataBaseConnection connection;
    public AsyncDataBaseConnection asynccon;

    public static void main(String[] args){
        new Server().run();
    }

    @Override
    public void run() {
        instance = this;
        logger = new GLogger(Level.ALL);
        netserver = new NetServer(8888, false, true, true, 1000*5);
        sendqueue = new SendQueue();
        connection = new DataBaseConnection("localhost", 8887);
        asynccon = new AsyncDataBaseConnection(connection);
    }
}
