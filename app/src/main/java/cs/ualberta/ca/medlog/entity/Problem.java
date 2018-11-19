/*
 * Class file for the Problem entity
 *
 * Authors: Thomas Roskewich, Tem Tamre
 * Contact: roskewic@ualberta.ca, ttamre@ualberta.ca
 * Created: October 27, 2018
 */

package cs.ualberta.ca.medlog.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Problem implements Serializable {
    private String title;
    private Date date;
    private int id = -1;
    private String description;
    ArrayList<Record> records = new ArrayList<Record>();

    public Problem(String title, Date date, String description){
        if(title.length() > 30){
            throw new IllegalArgumentException("Title is over the max title size of 30.");
        }else if(description.length() > 300){
            throw new IllegalArgumentException("Description is over the max description size of 300.");
        }
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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
        newRecord.setId(records.size());
        records.add(newRecord);
    }

    public void removeRecord(int index){
        for(int i = index; i < records.size(); i++){
            records.get(i).setId(records.get(i).getId() - 1);
        }
        records.remove(index);
    }


    public String toString(){
        return this.title;
    }

}
