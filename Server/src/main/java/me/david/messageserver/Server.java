package me.david.messageserver;

import io.netty.channel.epoll.Epoll;
import me.david.messagecore.logger.GLogger;
import me.david.messageserver.database.AsyncDataBaseConnection;
import me.david.messageserver.database.DataBaseConnection;
import me.david.messageserver.network.NetServer;
import me.david.messageserver.sendqueue.SendQueue;
import me.david.messageserver.webserver.WebServer;

import java.net.InetSocketAddress;
import java.util.logging.Level;

public class Server implements Runnable {

    public static Server instance;
    public GLogger logger;
    public DataBaseConnection connection;
    public AsyncDataBaseConnection asynccon;
    public NetServer netserver;
    public SendQueue sendqueue;
    public WebServer webserver;

    public static void main(String[] args){
        new Server().run();
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Starting/Main Thread");
        instance = this;
        logger = new GLogger(Level.ALL);
        connection = new DataBaseConnection("localhost", 8887);
        asynccon = new AsyncDataBaseConnection(connection);
        netserver = new NetServer(8888, false, true, true, 1000*5);
        netserver.start();
        sendqueue = new SendQueue();
        webserver = new WebServer(new InetSocketAddress("localhost", 8000), Epoll.isAvailable(), true, 1000*8);
        webserver.start();
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Stop Thread"));
    }

    public void stop(){
        netserver.shutdown();
        webserver.shutdown();
        connection.shutdown();
    }
}
