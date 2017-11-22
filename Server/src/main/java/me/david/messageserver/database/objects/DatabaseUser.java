package me.david.messageserver.database.objects;

import me.david.messagecore.account.AccountStatus;
import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

import java.util.ArrayList;

public class DatabaseUser extends DataBaseObject {

    private String id, name, email, password;
    private ArrayList<String> sessions;
    private long createt;
    private AccountStatus status;

    @Override
    public Document write() {
        return new Document("id", id).append("name", name).append("email", email)
                .append("password", password).append("sessions", sessions).append("status", status.getId())
                .append("created", createt);
    }

    @Override
    public void read(Document document) {
        id = document.getString("id");
        name = document.getString("name");
        email = document.getString("email");
        password = document.getString("password");
        sessions = (ArrayList<String>) document.get("sessions");
        status = AccountStatus.fromId(document.getInteger("status"));
        createt = document.getLong("createt");
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

    public ArrayList<String> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<String> sessions) {
        this.sessions = sessions;
    }
}
