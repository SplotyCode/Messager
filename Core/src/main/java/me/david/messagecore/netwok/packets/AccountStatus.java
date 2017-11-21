package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

/*
 * When the user change the Account Status
 */
public class AccountStatus implements Packet {

    private boolean status;

    public AccountStatus(){}
    public AccountStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        status = bytebuf.readBoolean();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeBoolean(status);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
