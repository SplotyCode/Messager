package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

/*
 * When a User manually logging in
 */
public class LoginPacket implements Packet {

    private String user, password, mac;

    public LoginPacket(){}
    public LoginPacket(String user, String password, String mac){
        this.user = user;
        this.password = password;
        this.mac = mac;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        user = bytebuf.readString();
        password = bytebuf.readString();
        mac = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(user);
        bytebuf.writeString(password);
        bytebuf.writeString(mac);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
