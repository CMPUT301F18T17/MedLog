package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

public class Problem {

    private String title;
    private Date date;
    private String description;
    ArrayList<Record> records = new ArrayList<Record>();

    Problem(String title, Date date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

    void setTitle(String newTitle){
        title = newTitle;
    }

    String getTitle(){
        return title;
    }

    void setDate(Date newDate){
        date = newDate;
    }

    Date getDate(){
        return date;
    }

    void setDescription(String newDescription){
        description = newDescription;
    }

    String getDescription(){
        return description;
    }

    void addRecord(Record newRecord){
        records.add(newRecord);
    }

    ArrayList<Record> getRecords(){
        return records;
    }
}
