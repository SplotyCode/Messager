package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class LoginResponePacket implements Packet {

    private String sessionid, userid;

    public LoginResponePacket(String sessionid, String userid){
        this.sessionid = sessionid;
        this.userid = userid;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        sessionid = bytebuf.readString();
        userid = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(sessionid);
        bytebuf.writeString(userid);
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
