package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;

public class SendMessage implements Packet {

    private String session, chatid, message;
    private long sended;

    public SendMessage(){}
    public SendMessage(String session, String chatid, String message, long sended){
        this.session = session;
        this.chatid = chatid;
        this.message = message;
        this.sended = sended;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        session = bytebuf.readString();
        chatid = bytebuf.readString();
        message = bytebuf.readString();
        sended = bytebuf.readLong();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(message);
        bytebuf.writeString(chatid);
        bytebuf.writeString(message);
        bytebuf.writeLong(sended);
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSended() {
        return sended;
    }

    public void setSended(long sended) {
        this.sended = sended;
    }
}
