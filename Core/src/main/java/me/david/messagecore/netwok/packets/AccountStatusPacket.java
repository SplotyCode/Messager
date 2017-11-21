package me.david.messagecore.netwok.packets;

import me.david.messagecore.account.AccountStatus;
import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class AccountStatusPacket implements Packet {

    private String session;
    private AccountStatus status;

    public AccountStatusPacket(){}
    public AccountStatusPacket(String session, AccountStatus status) {
        this.session = session;
        this.status = status;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        session = bytebuf.readString();
        status = bytebuf.readEnum(AccountStatus.class);
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(session);
        bytebuf.writeEnum(status);
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
