package me.david.messageserver.database.objects;

import org.bson.Document;

public abstract class DataBaseObject {

    public abstract Document write();

    public abstract void read(Document document);
}
