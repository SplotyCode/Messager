package me.david.messagecore.account;

/*
 * Days for the TimeTable
 */
public enum  TimeTableDay {

    MONDAY(0),
    THUSEDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4);

    private final int id;

    TimeTableDay(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static TimeTableDay getById(int id){
        for(TimeTableDay day : values())
            if(day.getId() == id)
                return day;
        return TimeTableDay.MONDAY;
    }
}
