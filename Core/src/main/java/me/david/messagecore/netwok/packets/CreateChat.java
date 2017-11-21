package me.david.messagecore.netwok.packets;

import me.david.messagecore.netwok.Packet;
import me.david.messagecore.netwok.PacketSerializer;

import java.io.IOException;
import java.util.ArrayList;

public class CreateChat implements Packet {

    private String session, name;
    private ArrayList<String> users;
    private long created;

    public CreateChat(){

    }

    public CreateChat(String session, String name, ArrayList<String> users, long created) {
        this.session = session;
        this.name = name;
        this.users = users;
        this.created = created;
    }

    @Override
    public void read(PacketSerializer bytebuf) throws IOException {
        session = bytebuf.readString();
        name = bytebuf.readString();
        users = bytebuf.readStringList();
        created = bytebuf.readLong();
    }

    @Override
    public void write(PacketSerializer bytebuf) throws IOException {
        bytebuf.writeString(session);
        bytebuf.writeString(name);
        bytebuf.writeStringList(users);
        bytebuf.writeLong(created);
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
