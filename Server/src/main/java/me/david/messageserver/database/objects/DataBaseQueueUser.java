package me.david.messageserver.database.objects;

import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

import java.util.List;

public class DataBaseQueueUser extends DataBaseObject {

    private String user;
    private String mac;
    private List<String> messageids;

    @Override
    public Document write() {
        return new Document("user", user).append("messageids", messageids).append("mac", mac);
    }

    @Override
    public void read(Document document) {
        user = document.getString("user");
        messageids = (List<String>) document.get("messageids");
        mac = document.getString("mac");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getMessageids() {
        return messageids;
    }

    public void setMessageids(List<String> messageids) {
        this.messageids = messageids;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
