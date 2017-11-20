package me.david.messageserver.database.objects;

import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

import java.util.List;

public class DataBaseChat extends DataBaseObject {

    private String chatid, title;
    private List<String> userids;

    @Override
    public Document write() {
        return new Document("chatid", chatid).append("title", title).append("userids", userids);
    }

    @Override
    public void read(Document document) {
        chatid = document.getString("chatid");
        title = document.getString("title");
        userids = (List<String>) document.get("userids");
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }
}
