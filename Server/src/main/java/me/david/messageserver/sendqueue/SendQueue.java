package me.david.messageserver.sendqueue;

import me.david.messageserver.Server;
import me.david.messageserver.database.objects.DataBaseMessage;
import me.david.messageserver.database.objects.DataBaseQueueUser;
import me.david.messageserver.database.objects.DataBaseSession;
import me.david.messageserver.database.objects.DatabaseUser;

import java.util.Collections;

public class SendQueue {

    public void addToQueue(DataBaseMessage message){
        for(String userid : Server.instance.connection.getChat(message.getChatid()).getUserids()){
            DatabaseUser user = Server.instance.connection.getUser(userid);
            for(String sessionid : user.getSessions()){
                DataBaseQueueUser queue = Server.instance.connection.getQueue(userid, Server.instance.connection.getSession(sessionid).getMac());
                if(queue == null){
                    queue = new DataBaseQueueUser();
                    queue.setUser(userid);
                    queue.setMessageids(Collections.singletonList(message.getMessageid()));
                    Server.instance.connection.addQueue(queue);
                }else {
                    queue.getMessageids().add(message.getMessageid());
                    Server.instance.connection.updateQueue(queue);
                }
            }
        }
    }
}
