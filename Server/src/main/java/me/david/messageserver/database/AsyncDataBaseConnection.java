package me.david.messageserver.database;

import me.david.messageserver.database.objects.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncDataBaseConnection {

    private final DataBaseConnection connection;
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

    public void getUserbyName(Consumer<DatabaseUser> cb, String name){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getUserbyName(name)));
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

    public void addTimetable(DataBaseTimetable timetable, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addTimeTable(timetable);
            run.run();
        });
    }

    public void getTimeTable(Consumer<DataBaseTimetable> cb, String user){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getTimeTable(user)));
    }

    public void updateTimeTable(DataBaseTimetable timetable, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateTimeTable(timetable);
            run.run();
        });
    }

    public void addClassHour(DataBaseClassHour classhour, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addClassHour(classhour);
            run.run();
        });
    }

    public void getClassHour(Consumer<DataBaseClassHour> cb, String id){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getClassHour(id)));
    }

    public void updateClassHour(DataBaseClassHour classhour, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateClassHour(classhour);
            run.run();
        });
    }

    public void sessionIdExsits(Consumer<Boolean> cb, String sessionid){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.sessionIdExsits(sessionid)));
    }

    public void getSession(Consumer<DataBaseSession> cb, String sessionid){
        exec.submit(() -> cb.accept(AsyncDataBaseConnection.this.connection.getSession(sessionid)));
    }

    public void addSession(DataBaseSession session, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.addSession(session);
            run.run();
        });
    }

    public void updateSession(DataBaseSession session, Runnable run){
        exec.submit(() -> {
            AsyncDataBaseConnection.this.connection.updateSession(session);
            run.run();
        });
    }


}
