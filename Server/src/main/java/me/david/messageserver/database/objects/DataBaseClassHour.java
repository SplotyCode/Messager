package me.david.messageserver.database.objects;

import me.david.messagecore.account.TimeTableDay;
import me.david.messageserver.database.DataBaseObject;
import org.bson.Document;

public class DataBaseClassHour extends DataBaseObject {

    private TimeTableDay day;
    private int id, hour, room;
    private String name;

    @Override
    public Document write() {
        return new Document("id", id).append("day", day.getId()).append("hour", hour).append("room", room)
                .append("name", name);
    }

    @Override
    public void read(Document document) {
        id = document.getInteger("id");
        day = TimeTableDay.getById(document.getInteger("day"));
        hour = document.getInteger("hour");
        room = document.getInteger("room");
        name = document.getString("name");
    }

    public TimeTableDay getDay() {
        return day;
    }

    public void setDay(TimeTableDay day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
