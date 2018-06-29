/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Clock class 
 */
package classes;

public class Clock {

    String clockID;
    String cDate;
    String TimeIn;
    String TimeOut;
    String vUserName;

    // constructor 
    public Clock(String clockID, String cDate, String TimeIn, String TimeOut, String vUserName) {
        this.clockID = clockID;
        this.cDate = cDate;
        this.TimeIn = TimeIn;
        this.TimeOut = TimeOut;
        this.vUserName = vUserName;
    }

    // create getter 
    public String getClockID() {
        return clockID;
    }
    // create setter

    public void setClockID(String clockID) {
        this.clockID = clockID;
    }
    // create getter 
    public String getCDate() {
        return cDate;
    }
    // create getter 
    public String getTimeIn() {
        return TimeIn;
    }
    // create getter 
    public String getTimeOut() {
        return TimeOut;
    }

}
