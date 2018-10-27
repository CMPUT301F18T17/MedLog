package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

public class Problem {

    private String title;
    private Date date;
    private String description;
    ArrayList<Record> records = new ArrayList<Record>();

    public Problem(String title, Date date, String description){
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public void setTitle(String newTitle){
        title = newTitle;
    }

    public String getTitle(){
        return title;
    }

    public void setDate(Date newDate){
        date = newDate;
    }

    public Date getDate(){
        return date;
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }

    public String getDescription(){
        return description;
    }

    public void addRecord(Record newRecord){
        records.add(newRecord);
    }

    public ArrayList<Record> getRecords(){
        return records;
    }
}
