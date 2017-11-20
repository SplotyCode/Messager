package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class AuthPacket implements Packet {

    private String session;

    public AuthPacket(String session){
        this.session = session;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        session = bytebuf.readString();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(session);
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
