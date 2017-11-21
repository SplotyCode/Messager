package me.david.messageserver.database.objects;

import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

import java.util.List;

public class DataBaseTimetable extends DataBaseObject {

    private String user;
    private List<String> classes;

    @Override
    public Document write() {
        return new Document("user", user).append("classes", classes);
    }

    @Override
    public void read(Document document) {
        user = document.getString("user");
        classes = (List<String>) document.get("classes");
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
}
