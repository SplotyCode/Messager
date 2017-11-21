package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class AuthRespone implements Packet {

    private boolean failture;

    public AuthRespone(){}
    public AuthRespone(boolean failture){
        this.failture = failture;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        failture = bytebuf.readBoolean();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeBoolean(failture);
    }

    public boolean isFailture() {
        return failture;
    }

    public void setFailture(boolean failture) {
        this.failture = failture;
    }
}
