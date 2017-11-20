package me.david.messageserver.database.objects;

import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

public class DataBaseMessage extends DataBaseObject {

    private String message, userid, chatid, messageid;
    private long sended;

    @Override
    public Document write() {
        return new Document("message", message).append("userid", userid).append("chatid", chatid)
                .append("messageid", messageid).append("sended", sended);
    }

    @Override
    public void read(Document document) {
        message = document.getString("message");
        userid = document.getString("userid");
        chatid = document.getString("chatid");
        messageid = document.getString("messageid");
        sended = document.getLong("sended");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public long getSended() {
        return sended;
    }

    public void setSended(long sended) {
        this.sended = sended;
    }
}
