package me.david.messageclient;

public class Client {

    public static Client instance;
    public DeviceManager devicemanager;

    public Client(DeviceManager device){
        instance = this;
        devicemanager = device;
    }


}
