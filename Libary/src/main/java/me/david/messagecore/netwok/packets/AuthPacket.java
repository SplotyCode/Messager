package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

/*
 * When a clint auth with the stored user and session id
 */
public class AuthPacket implements Packet {

    private String userid, session, mac;

    public AuthPacket(){}
    public AuthPacket(String userid, String session, String mac){
        this.userid = userid;
        this.session = session;
        this.mac = mac;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        userid = bytebuf.readString();
        session = bytebuf.readString();
        mac = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(userid);
        bytebuf.writeString(session);
        bytebuf.writeString(mac);
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
