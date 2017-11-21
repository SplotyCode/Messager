package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class LoginResponePacket implements Packet {

    private boolean failed;
    private String sessionid, userid;

    public LoginResponePacket(){}
    public LoginResponePacket(boolean failed){
        this.failed = failed;
    }
    public LoginResponePacket(String sessionid, String userid){
        this.failed = false;
        this.sessionid = sessionid;
        this.userid = userid;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        failed = bytebuf.readBoolean();
        sessionid = bytebuf.readString();
        userid = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeBoolean(failed);
        bytebuf.writeString(sessionid);
        bytebuf.writeString(userid);
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
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
