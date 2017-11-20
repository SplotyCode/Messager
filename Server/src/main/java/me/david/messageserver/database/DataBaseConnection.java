package me.david.messageserver.database;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import me.david.messageserver.database.objects.DatabaseUser;
import org.bson.Document;

public class DataBaseConnection {

    private MongoClient client;
    private MongoDatabase database;

    private final UpdateOptions UPSERT = new UpdateOptions().upsert(true);

    public DataBaseConnection(){
        this.client = new MongoClient();
        this.database = client.getDatabase("messager");
    }

    public void updateUser(DatabaseUser user){
        MongoCollection<Document> coll = database.getCollection("users");
        coll.replaceOne(Filters.eq("name", user.getName()), user.write());
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
        coll.deleteOne(Filters.eq("name", user.getName()));
    }
}
