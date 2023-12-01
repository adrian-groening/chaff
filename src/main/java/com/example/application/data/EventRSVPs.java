package com.example.application.data;

import com.example.application.services.DB;

import java.sql.SQLException;

public class EventRSVPs {
    int rsvpID;
    int eventID;
    int userID;
    public EventRSVPs() {

    }

    public EventRSVPs(int rsvpID, int eventID, int userID) {
        this.rsvpID = rsvpID;
        this.eventID = eventID;
        this.userID = userID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setRsvpID(int rsvpID) {
        this.rsvpID = rsvpID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getEventID() {
        return eventID;
    }

    public int getRsvpID() {
        return rsvpID;
    }

    public int getUserID() {
        return userID;
    }




}
