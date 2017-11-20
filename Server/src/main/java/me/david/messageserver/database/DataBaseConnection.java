package me.david.messageserver.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.david.messageserver.database.objects.DataBaseChat;
import me.david.messageserver.database.objects.DataBaseMessage;
import me.david.messageserver.database.objects.DataBaseQueueUser;
import me.david.messageserver.database.objects.DatabaseUser;
import org.bson.Document;

public class DataBaseConnection {

    private MongoClient client;
    private MongoDatabase database;

    private final UpdateOptions UPSERT = new UpdateOptions().upsert(true);

    public DataBaseConnection(String host, int port){
        this.client = new MongoClient(host, port);
        this.database = client.getDatabase("messager");
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
}
