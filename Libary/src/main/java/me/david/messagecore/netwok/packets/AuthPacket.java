package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

/*
 * When a clint auth with the stored user and session id
 */
public class AuthPacket implements Packet {

    private String userid, session;

    public AuthPacket(){}
    public AuthPacket(String userid, String session){
        this.userid = userid;
        this.session = session;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        userid = bytebuf.readString();
        session = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(userid);
        bytebuf.writeString(session);
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
}
