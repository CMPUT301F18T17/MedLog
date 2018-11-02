/*
 * Class file for the Problem entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 27, 2018
 */

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

    public String getTitle(){
        return title;
    }

    public Date getDate(){
        return date;
    }

    public String getDescription(){
        return description;
    }

    public ArrayList<Record> getRecords(){
        return records;
    }


    public void setTitle(String newTitle){
        title = newTitle;
    }

    public void setDate(Date newDate){
        date = newDate;
    }

    public void setDescription(String newDescription){
        description = newDescription;
    }

    public void addRecord(Record newRecord){
        records.add(newRecord);
    }


}
