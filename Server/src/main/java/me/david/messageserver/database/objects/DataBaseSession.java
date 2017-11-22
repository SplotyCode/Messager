package me.david.messageserver.database.objects;

import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

public class DataBaseSession extends DataBaseObject {

    private boolean online;
    private String mac, session;
    private long lastonline;

    @Override
    public Document write() {
        return new Document("online", online).append("mac", mac).append("session", session)
                .append("lastonline", lastonline);
    }

    @Override
    public void read(Document document) {
        online = document.getBoolean("online");
        mac = document.getString("mac");
        session = document.getString("session");
        lastonline = document.getLong("lastonline");
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public long getLastonline() {
        return lastonline;
    }

    public void setLastonline(long lastonline) {
        this.lastonline = lastonline;
    }
}
