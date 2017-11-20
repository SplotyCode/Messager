package me.david.messageclient;

import me.david.messagecore.logger.GLogger;

import java.util.logging.Level;

/*
 * The Main from the Client
 */
public class Client {

    public static Client instance;
    public DeviceManager devicemanager;
    public GLogger logger;

    public Client(DeviceManager device){
        instance = this;
        logger = new GLogger(Level.ALL);
        devicemanager = device;
    }


}
