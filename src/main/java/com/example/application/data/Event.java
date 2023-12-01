package com.example.application.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Event {
    int eventId,eventAttendanceLimit, eventAttendanceCount;
    String nameOfEvent, description, category, eventStarTime, eventEndTime, eventDate, eventVenue;
    public Event() {

    }
    public Event(int eventId, String nameOfEvent, String description, String category, String eventStarTime, String eventEndTime, String eventDate, String eventVenue, int eventAttendanceLimit, int eventAttendanceCount) {
        this.eventId = eventId;
        this.nameOfEvent = nameOfEvent;
        this.description = description;
        this.category = category;
        this.eventStarTime = eventStarTime;
        this.eventEndTime = eventEndTime;
        this.eventDate = eventDate;
        this.eventVenue = eventVenue;
        this.eventAttendanceLimit = eventAttendanceLimit;
        this.eventAttendanceCount = eventAttendanceCount;
    }

    public int getEventId() {
        return eventId;
    }

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getEventStarTime() {
        return eventStarTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public int getEventAttendanceLimit() {
        return eventAttendanceLimit;
    }

    public int getEventAttendanceCount() {
        return eventAttendanceCount;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEventStarTime(String eventStarTime) {
        this.eventStarTime = eventStarTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setEventAttendanceLimit(int attendanceLimit) {
        this.eventAttendanceLimit = attendanceLimit;
    }

    public void setEventAttendanceCount(int eventAttendanceCount) {
        this.eventAttendanceCount = eventAttendanceCount;
    }





}
