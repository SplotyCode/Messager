package me.david.messageserver.database;

import me.david.messageserver.database.objects.DatabaseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncDataBaseConnection {

    private DataBaseConnection connection;
    private ExecutorService exec;

    public AsyncDataBaseConnection(DataBaseConnection connection){
        this.connection = connection;
        this.exec = Executors.newCachedThreadPool();
    }

    public void updateUser(DatabaseUser user, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateUser(user);
            run.run();
        });
    }

    public void addUser(DatabaseUser user, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addUser(user);
            run.run();
        });
    }

    public void getUser(String id, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.getUser(id);
            run.run();
        });
    }
}
