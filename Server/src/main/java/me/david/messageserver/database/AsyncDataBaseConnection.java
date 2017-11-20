package me.david.messageserver.database;

import me.david.messageserver.database.objects.DataBaseChat;
import me.david.messageserver.database.objects.DataBaseMessage;
import me.david.messageserver.database.objects.DataBaseQueueUser;
import me.david.messageserver.database.objects.DatabaseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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

    public void getUser(Consumer<DatabaseUser> cb, String id){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getUser(id)));
    }

    public void addQueue(DataBaseQueueUser queue, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addQueue(queue);
            run.run();
        });
    }

    public void getQueue(Consumer<DataBaseQueueUser> cb, String userid){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getQueue(userid)));
    }

    public void updateQueue(DataBaseQueueUser queue, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateQueue(queue);
            run.run();
        });
    }

    public void addMessage(DataBaseMessage message, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addMessage(message);
            run.run();
        });
    }

    public void getMessage(Consumer<DataBaseMessage> cb, String messageid){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getMessage(messageid)));
    }

    public void addChat(DataBaseChat chat, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addChat(chat);
            run.run();
        });
    }

    public void getChat(Consumer<DataBaseChat> cb, String chatid){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getChat(chatid)));
    }

    public void updateChat(DataBaseChat chat, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateChat(chat);
            run.run();
        });
    }


}
