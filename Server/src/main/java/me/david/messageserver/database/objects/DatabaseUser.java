package me.david.messageserver.database.objects;

import me.david.messagecore.account.AccountStatus;
import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

public class DatabaseUser extends DataBaseObject {

    private String id, name, email, password, sessionid;
    private long lastonline, createt;
    private boolean online;
    private AccountStatus status;

    @Override
    public Document write() {
        return new Document("id", id).append("name", name).append("email", email)
                .append("password", password).append("lastonline", lastonline)
                .append("online", online).append("status", status.getId())
                .append("created", createt).append("sessionid", sessionid);
    }

    @Override
    public void read(Document document) {
        id = document.getString("id");
        name = document.getString("name");
        email = document.getString("email");
        password = document.getString("password");
        lastonline = document.getLong("lastonline");
        online = document.getBoolean("online");
        status = AccountStatus.fromId(document.getInteger("status"));
        createt = document.getLong("createt");
        sessionid = document.getString("sessionid");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getLastonline() {
        return lastonline;
    }

    public void setLastonline(long lastonline) {
        this.lastonline = lastonline;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreatet() {
        return createt;
    }

    public void setCreatet(long createt) {
        this.createt = createt;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
