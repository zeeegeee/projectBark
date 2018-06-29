/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Trip class 
 */
package classes;

public class trip {

    public String tID;
    public String tTask;
    public String tLocation;
    public int tMileage;
    public int tDuration;
    public String vUserName;

    // Constructors
    public trip(String tID, String tTask, String tLocation, int tMileage, int tDuration, String vUserName) {
        this.tID = tID;
        this.tTask = tTask;
        this.tLocation = tLocation;
        this.tMileage = tMileage;
        this.tDuration = tDuration;
        this.vUserName = vUserName;
    }
    // create getter
    public String getTID() {
        return tID;
    }
    // create setter
    public void setTID(String tID) {
        this.tID = tID;
    }
    // create getter
    public String getTTask() {
        return tTask;
    }
    // create setter
    public void setTTask(String tTask) {
        this.tTask = tTask;
    }
    // create getter
    public String getTLocation() {
        return tLocation;
    }
    // create setter
    public void setTLocation(String tLocation) {
        this.tLocation = tLocation;
    }
    // create getter
    public int getTMileage() {
        return tMileage;
    }
    // create setter
    public void setTMileage(int tMileage) {
        this.tMileage = tMileage;
    }
    // create getter
    public int getTDuration() {
        return tDuration;
    }
    // create setter
    public void setTDuration(int tDuration) {
        this.tDuration = tDuration;
    }
    // create getter
    public String getVUserName() {
        return vUserName;
    }
    // create setter
    public void setVUserName(String vUserName) {
        this.vUserName = vUserName;
    }

}