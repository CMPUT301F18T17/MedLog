package cs.ualberta.ca.medlog.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 *     Description: <br>
 *         This class represents a patient's problem in the application, this problem possesses an
 *         id, title, date, description and records. Furthermore there are getters and setters for
 *         these components.
 * </p>
 * <p>
 *     Issues: <br>
 *         None.
 * </p>
 *
 * @author Thomas Roskewich, Calvin Chomyc, Tem Tamre
 * @version 1.3
 * @see Record
 */
public class Problem {
    private int id = -1;
    private String title;
    private Date date;
    private String description;
    private ArrayList<Record> records = new ArrayList<>();

    /**
     * Constructs the problem with the given title, date and description. Makes sure to check if
     * the title or description are too long.
     * @param title The title.
     * @param date The date.
     * @param description The description.
     * @throws IllegalArgumentException If the title or description are too long.
     */
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
     * Retrieves the problem id.
     * @return The id.
     */
    public int getId(){
        return id;
    }

    /**
     * Sets the problem id to the new provided one.
     * @param id The new id.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Retrieves the problem title.
     * @return The title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * Sets the problem title to the new provided one.
     * @param newTitle The new title.
     */
    public void setTitle(String newTitle){
        title = newTitle;
    }

    /**
     * Retrieves the date of creation for the problem.
     * @return The creation date.
     */
    public Date getDate(){
        return date;
    }

    /**
     * Sets the problem creation date to the new provided one.
     * @param newDate The new date.
     */
    public void setDate(Date newDate){
        date = newDate;
    }

    /**
     * Retrieves the problem's description.
     * @return The description.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the description to the new provided one.
     * @param newDescription The new description.
     */
    public void setDescription(String newDescription){
        description = newDescription;
    }

    /**
     * Retrieves all of the problem's records.
     * @return ArrayList of the problem's records.
     */
    public ArrayList<Record> getRecords(){
        return records;
    }

    /**
     * Add a provided record to the problem's records.
     * @param newRecord The record to add.
     */
    public void addRecord(Record newRecord){
        newRecord.setId(records.size());
        records.add(newRecord);
    }

    /**
     * Removes the problem record with the given id/index.
     * @param index The id/index of the record to remove.
     */
    public void removeRecord(int index){
        for(int i = index; i < records.size(); i++){
            records.get(i).setId(records.get(i).getId() - 1);
        }
        records.remove(index);
    }

    /**
     * Retrieves a string representation of the patient based on their title.
     * @return The string representation.
     */
    public String toString(){
        return this.title;
    }

}
