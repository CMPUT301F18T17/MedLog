/**
 * <p>
 *     Description: <br>
 *         Class representing a Patients problem.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * @version 1.0
 * @see cs.ualberta.ca.medlog.entity.Record
 */
package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

public class Problem {
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

    /**
     * <p>Get the problems ID</p>
     * @return The problems id.
     */
    public int getId(){
        return id;
    }

    /**
     * <p>Set the problem ID.</p>
     * @param id The id to set the id to.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * <p>Get the title of the problem.</p>
     * @return The title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * <p>Get the date of creation for the problem.</p>
     * @return The creation date.
     */
    public Date getDate(){
        return date;
    }

    /**
     * <p>Get the problems description.</p>
     * @return The description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * <p>Get the list of records this problem holds.</p>
     * @return The records it holds.
     */
    public ArrayList<Record> getRecords(){
        return records;
    }


    /**
     * <p>Change the problems title.</p>
     * @param newTitle The new title.
     */
    public void setTitle(String newTitle){
        title = newTitle;
    }

    /**
     * <p>Change the problems creation date.</p>
     * @param newDate The new date.
     */
    public void setDate(Date newDate){
        date = newDate;
    }

    /**
     * <p>Change the problems description.</p>
     * @param newDescription The new description.
     */
    public void setDescription(String newDescription){
        description = newDescription;
    }

    /**
     * Add a record to the problem.
     * @param newRecord the record to add.
     */
    public void addRecord(Record newRecord){
        newRecord.setId(records.size());
        records.add(newRecord);
    }

    /**
     * <p>Remove a record with the provided ID.</p>
     * @param index The index/id of the record.
     */
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
