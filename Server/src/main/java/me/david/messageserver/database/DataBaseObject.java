package me.david.messageserver.database;

import org.bson.Document;

public abstract class DataBaseObject {

    public abstract Document write();

    public abstract void read(Document document);
}
