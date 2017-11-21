package me.david.messageserver.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.david.messageserver.database.objects.*;
import org.bson.Document;

import javax.print.Doc;

public class DataBaseConnection {

    private MongoClient client;
    private MongoDatabase database;

    public DataBaseConnection(String host, int port){
        this.client = new MongoClient(host, port);
        this.database = client.getDatabase("messager");
    }

    public void shutdown(){
        client.close();
    }

    public void updateUser(DatabaseUser user){
        MongoCollection<Document> coll = database.getCollection("users");
        coll.replaceOne(Filters.eq("id", user.getId()), user.write());
    }

    public DatabaseUser getUser(String id){
        MongoCollection<Document> coll = database.getCollection("users");
        DatabaseUser user = new DatabaseUser();
        Document doc = coll.find(Filters.eq("id", id)).first();
        if(doc == null)return null;
        user.read(doc);
        return user;
    }

    /*
     * Username is changeable
     * If you want something unique use the userid
     */
    public DatabaseUser getUserbyName(String name){
        MongoCollection<Document> coll = database.getCollection("users");
        DatabaseUser user = new DatabaseUser();
        Document doc = coll.find(Filters.eq("name", name)).first();
        if(doc == null)return null;
        user.read(doc);
        return user;
    }

    public void addUser(DatabaseUser user){
        MongoCollection<Document> coll = database.getCollection("users");
        coll.insertOne(user.write());
    }

    public void addQueue(DataBaseQueueUser queue){
        MongoCollection<Document> coll = database.getCollection("queue");
        coll.insertOne(queue.write());
    }

    public DataBaseQueueUser getQueue(String userid){
        MongoCollection<Document> coll = database.getCollection("queue");
        DataBaseQueueUser user = new DataBaseQueueUser();
        Document doc = coll.find(Filters.eq("user", userid)).first();
        if(doc == null)return null;
        user.read(doc);
        return user;
    }

    public void updateQueue(DataBaseQueueUser queue){
        MongoCollection<Document> coll = database.getCollection("queue");
        coll.replaceOne(Filters.eq("user", queue.getUser()), queue.write());
    }

    public void addMessage(DataBaseMessage message){
        MongoCollection<Document> coll = database.getCollection("messages");
        coll.insertOne(message.write());
    }

    public DataBaseMessage getMessage(String messageid){
        MongoCollection<Document> coll = database.getCollection("messages");
        DataBaseMessage user = new DataBaseMessage();
        Document doc = coll.find(Filters.eq("messageid", messageid)).first();
        if(doc == null)return null;
        user.read(doc);
        return user;
    }

    public void addChat(DataBaseChat chat){
        MongoCollection<Document> coll = database.getCollection("chats");
        coll.insertOne(chat.write());
    }

    public DataBaseChat getChat(String chatid){
        MongoCollection<Document> coll = database.getCollection("chats");
        DataBaseChat user = new DataBaseChat();
        Document doc = coll.find(Filters.eq("chatid", chatid)).first();
        if(doc == null)return null;
        user.read(doc);
        return user;
    }

    public void updateChat(DataBaseChat chat){
        MongoCollection<Document> coll = database.getCollection("chats");
        coll.replaceOne(Filters.eq("chatid", chat.getChatid()), chat.write());
    }

    public DataBaseTimetable getTimeTable(String userid){
        MongoCollection<Document> coll = database.getCollection("timetable");
        DataBaseTimetable timetable = new DataBaseTimetable();
        Document doc = coll.find(Filters.eq("user", userid)).first();
        if(doc == null) return null;
        timetable.read(doc);
        return timetable;
    }

    public void addTimeTable(DataBaseTimetable timetable){
        MongoCollection<Document> coll = database.getCollection("timetable");
        coll.insertOne(timetable.write());
    }

    public void updateTimeTable(DataBaseTimetable timetable){
        MongoCollection<Document> collection = database.getCollection("timetable");
        collection.replaceOne(Filters.eq("user", timetable.getUser()), timetable.write());
    }

    public DataBaseClassHour getClassHour(String id){
        MongoCollection<Document> coll = database.getCollection("classhour");
        DataBaseClassHour classhour = new DataBaseClassHour();
        Document doc = coll.find(Filters.eq("id", id)).first();
        if(doc == null) return null;
        classhour.read(doc);
        return classhour;
    }

    public void addClassHour(DataBaseClassHour classhour){
        MongoCollection<Document> coll = database.getCollection("classhour");
        coll.insertOne(classhour.write());
    }

    public void updateClassHour(DataBaseClassHour classhour){
        MongoCollection<Document> collection = database.getCollection("classhour");
        collection.replaceOne(Filters.eq("id", classhour.getId()), classhour.write());
    }


}
