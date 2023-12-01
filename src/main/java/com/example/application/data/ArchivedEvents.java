package com.example.application.data;

public class ArchivedEvents {
    int archivedEventID, eventID, attendanceCount;
    String eventName, eventDate;

    public ArchivedEvents() {

    }

    public ArchivedEvents(int archivedEventID, int eventID, String eventName, String eventDate, int attendanceCount) {
        this.archivedEventID = archivedEventID;
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.attendanceCount = attendanceCount;
    }

    public int getArchivedEventID() {
        return archivedEventID;
    }

    public void setArchivedEventID(int archivedEventID) {
        this.archivedEventID = archivedEventID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
    }
}
