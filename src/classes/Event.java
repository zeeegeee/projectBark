/*
Authors: Moe, Sakshi, Olivia, Yui, and Caity
Date: 6/26/2018
Purpose: This is the Event class 
*/
package classes;

public class Event {
    public String eventID;
    public String eventName;
    public int maxVolunteers;
    public String eventDate;
    public String eventTime;
    public String eventLocation;
    public int eventDuration;
    
    // constructor 
    public Event(String eventID, String eventName, int maxVolunteers, String eventDate, String eventTime, String eventLocation, int eventDuration)
    {
        this.eventID = eventID;
        this.eventName = eventName;
        this.maxVolunteers = maxVolunteers;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.eventDuration = eventDuration;
    }
    // create getter 
    public String getEventID() {
        return eventID;
    }
    // create getter 
    public String getEventName() {
        return eventName;
    }
    // create getter 
    public int getMaxVolunteers() {
        return maxVolunteers;
    }
    // create getter 
    public String getEventDate() {
        return eventDate;
    }
    // create getter 
    public String getEventTime() {
        return eventTime;
    }
    // create getter 
    public String getEventLocation() {
        return eventLocation;
    }
    // create getter 
    public int getEventDuration() {
        return eventDuration;
    }   
    // create setter
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    // create setter 
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    // create setter 
    public void setMaxVolunteers(int maxVolunteers) {
        this.maxVolunteers = maxVolunteers;
    }
    // create setter 
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    // create setter 
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
    // create setter 
    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
    // create setter 
    public void setEventDuration(int eventDuration) {
        this.eventDuration = eventDuration;
    }

}

