package com.example.application.services;

import com.example.application.data.ArchivedEvents;
import com.example.application.data.Event;
import com.example.application.data.EventRSVPs;
import com.example.application.data.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    Connection con;
    PreparedStatement p;
    Statement s;
    ResultSet rs;
    String url, username, password;

    public DB() throws SQLException {
        url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5665079";
        username = "sql5665079";
        password = "Akz1DQ7rJI";
        openConnection();
    }

    public void openConnection() throws SQLException {
        con = DriverManager.getConnection(url, username, password);
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public String getFirstName(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT firstName from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("firstName");
        } else {
            return "";
        }
    }

    public String getLastName(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT lastName from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("lastName");
        } else {
            return "";
        }
    }

    public String getRole(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT role from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("role");
        } else {
            return "";
        }
    }

    public String getGender(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT gender from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("gender");
        } else {
            return "";
        }
    }

    public String getDateOfBirth(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT dateOfBirth from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("dateOfBirth");
        } else {
            return "";
        }
    }

    public String getEmail(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT email from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("email");
        } else {
            return "";
        }
    }

    public String getPhoneNumber(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT phoneNumber from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("phoneNumber");
        } else {
            return "";
        }
    }

    public String getPassword(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT password from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("password");
        } else {
            return "";
        }
    }

    public String getAddress(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT address from Users WHERE userID = " + userID);
        if (rs.next()) {
            return rs.getString("address");
        } else {
            return "";
        }
    }

    public int getID(String email) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT userID from Users WHERE email = " + email);
        if (rs.next()) {
            return rs.getInt("userID");
        } else {
            return -1;
        }
    }

    public void createVisitor(int userID, String firstName, String lastName, String gender, String dateOfBirth, String email, String phoneNumber, String password, String address) throws SQLException {
        p = con.prepareStatement("INSERT INTO Users VALUES (?,?,?,?,?,?,?,?,?,?)");
        p.setInt(1, userID);
        p.setString(2, "visitor");
        p.setString(3, firstName);
        p.setString(4, lastName);
        p.setString(5, gender);
        p.setString(6, dateOfBirth);
        p.setString(7, email);
        p.setString(8, phoneNumber);
        p.setString(9, password);
        p.setString(10, address);
        p.executeUpdate();
    }

    public void createEvent(int eventID, String nameOfEvent, String description, String category, String eventStarTime, String eventEndTime, String eventDate, String eventVenue, int attendanceLimit, int attendanceCount) throws SQLException {
        p = con.prepareStatement("INSERT INTO Event VALUES (?,?,?,?,?,?,?,?,?,?)");
        p.setInt(1, eventID);
        p.setString(2, nameOfEvent);
        p.setString(3, description);
        p.setString(4, category);
        p.setString(5, eventStarTime);
        p.setString(6, eventEndTime);
        p.setString(7, eventDate);
        p.setString(8, eventVenue);
        p.setString(2, nameOfEvent);
        p.setInt(9, attendanceLimit);
        p.setInt(10, 0);
        p.executeUpdate();
    }

    public void archiveEvent(int archivedEventID, int eventID, String eventName, String eventDate, int attendanceCount) throws SQLException {
        p = con.prepareStatement("INSERT INTO ArchivedEvents VALUES (?,?,?,?,?)");
        p.setInt(1, archivedEventID);
        p.setInt(2, eventID);
        p.setString(3, eventName);
        p.setString(4, eventDate);
        p.setInt(5, attendanceCount);
        p.executeUpdate();
    }

    public void createRSVP(int rsvpID, int eventID, int userID) throws SQLException {
        p = con.prepareStatement("INSERT INTO EventRSVPs VALUES (?,?,?)");
        p.setInt(1, rsvpID);
        p.setInt(2, eventID);
        p.setInt(3, userID);
        p.executeUpdate();
    }

    public void createFeedback(int feedbackID, int rating, int recommendation, String comment, int eventID, int userID) throws SQLException {
        p = con.prepareStatement("INSERT INTO EventFeedback VALUES (?,?,?,?,?, ?)");
        p.setInt(1, feedbackID);
        p.setInt(2, rating);
        p.setInt(3, recommendation);
        p.setString(4, comment);
        p.setInt(5, eventID);
        p.setInt(6, userID);
        p.executeUpdate();
    }

    public void deleteEvent(int eventID) throws SQLException {
        p = con.prepareStatement("DELETE FROM Event WHERE eventID=?");
        p.setInt(1, eventID);
        p.executeUpdate();
    }

    public void updateEventName(int eventID, String nameOfEvent) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET nameOfEvent=? WHERE eventID=?");
        p.setString(1, nameOfEvent);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventDescription(int eventID, String description) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET description=? WHERE eventID=?");
        p.setString(1, description);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventCategory(int eventID, String category) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET category=? WHERE eventID=?");
        p.setString(1, category);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventStarTime(int eventID, String eventStarTime) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET eventStarTime=? WHERE eventID=?");
        p.setString(1, eventStarTime);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventEndTime(int eventID, String eventEndTime) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET eventEndTime=? WHERE eventID=?");
        p.setString(1, eventEndTime);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventDate(int eventID, String eventDate) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET eventDate=? WHERE eventID=?");
        p.setString(1, eventDate);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventVenue(int eventID, String eventVenue) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET eventVenue=? WHERE eventID=?");
        p.setString(1, eventVenue);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public void updateEventAttendanceLimit(int eventID, String attendanceLimit) throws SQLException {
        p = con.prepareStatement("UPDATE Event SET attendanceLimit=? WHERE eventID=?");
        p.setString(1, attendanceLimit);
        p.setInt(2, eventID);
        p.executeUpdate();
    }
    public void incrementAttendance(int eventID) throws SQLException {
        int attendanceCount = getAttendanceCount(eventID);
        attendanceCount += 1;
        p = con.prepareStatement("UPDATE Event SET attendanceCount=? WHERE eventID=?");
        p.setInt(1, attendanceCount);
        p.setInt(2, eventID);
        p.executeUpdate();
    }

    public int getAttendanceCount(int eventID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT attendanceCount FROM Event WHERE eventID =" + eventID);
        if (rs.next()) {
            return rs.getInt("attendanceCount");
        } else {
            return -1;
        }
    }

    public int getAttendanceLimit(int eventID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT attendanceLimit FROM Event WHERE eventID =" + eventID);
        if (rs.next()) {
            return rs.getInt("attendanceLimit");
        } else {
            return -1;
        }
    }



    public boolean emailExists(String email) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT email from Users WHERE email= " + email);
        return rs.next();
    }
    public boolean userIDExists(int userID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT userID from Users WHERE userID= " + userID);
        return rs.next();
    }

    public boolean eventIDExists(int eventID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT eventID from Event WHERE eventID= " + eventID);
        return rs.next();
    }

    public List<Event> getEvents() throws SQLException {
        List<Event> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Event");
        while (rs.next()) {
            Event e = new Event();
            e.setEventId(rs.getInt("eventID"));
            e.setNameOfEvent(rs.getString("nameOfEvent"));
            e.setDescription(rs.getString("description"));
            e.setCategory(rs.getString("category"));
            e.setEventStarTime(rs.getString("eventStarTime"));
            e.setEventEndTime(rs.getString("eventEndTime"));
            e.setEventDate(rs.getString("eventDate"));
            e.setEventVenue(rs.getString("eventVenue"));
            e.setEventAttendanceLimit(rs.getInt("attendanceLimit"));
            e.setEventAttendanceCount(rs.getInt("attendanceCount"));
            list.add(e);
        }
        return list;
    }

    public List<ArchivedEvents> getArchivedEvents() throws SQLException {
        List<ArchivedEvents> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM ArchivedEvents");
        while (rs.next()) {
            ArchivedEvents ae = new ArchivedEvents();
            ae.setArchivedEventID(rs.getInt("archivedEventID"));
            ae.setEventID(rs.getInt("eventID"));
            ae.setEventName(rs.getString("eventName"));
            ae.setEventDate(rs.getString("eventDate"));
            ae.setEventDate(rs.getString("eventDate"));
            ae.setAttendanceCount(rs.getInt("attendanceCount"));
            list.add(ae);
        }
        return list;
    }

    public List<Users> getUsers() throws SQLException {
        List<Users> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM Users");
        while(rs.next()) {
            Users u = new Users();
            u.setUserID(rs.getInt("userID"));
            u.setRole(rs.getString("role"));
            u.setFirstName(rs.getString("firstName"));
            u.setLastName(rs.getString("lastName"));
            u.setGender(rs.getString("gender"));
            u.setDateOfBirth(rs.getString("dateOfBirth"));
            u.setEmail(rs.getString("email"));
            u.setPhoneNumber(rs.getString("phoneNumber"));
            u.setPassword(rs.getString("password"));
            u.setAddress(rs.getString("address"));
            list.add(u);
        }
        return list;
    }

    public List<Users> dashUsers() throws SQLException {
        List<Users> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT firstName, lastName, gender, dateOfBirth, email FROM Users");
        while(rs.next()) {
            Users u = new Users();
            u.setFirstName(rs.getString("firstName"));
            u.setLastName(rs.getString("lastName"));
            u.setGender(rs.getString("gender"));
            u.setDateOfBirth(rs.getString("dateOfBirth"));
            u.setEmail(rs.getString("email"));
            list.add(u);
        }
        return list;
    }

    public List<Event> dashEvents() throws SQLException {
        List<Event> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT nameOfEvent, category, eventDate, attendanceCount, attendanceLimit FROM Event");
        while(rs.next()) {
            Event e = new Event();
            e.setNameOfEvent(rs.getString("nameOfEvent"));
            e.setCategory(rs.getString("category"));
            e.setEventDate(rs.getString("eventDate"));
            e.setEventAttendanceCount(rs.getInt("attendanceCount"));
            e.setEventAttendanceLimit(rs.getInt("attendanceLimit"));
            list.add(e);
        }
        return list;
    }

    public List<EventRSVPs> getRSVPs() throws SQLException {
        List<EventRSVPs> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM EventRSVPs");
        while(rs.next()) {
            EventRSVPs r = new EventRSVPs();
            r.setRsvpID(rs.getInt("rsvpID"));
            r.setEventID(rs.getInt("eventID"));
            r.setUserID(rs.getInt("userID"));
            list.add(r);
        }
        return list;
    }

    public List<EventRSVPs> getUserRSVPs(int userID) throws SQLException {
        List<EventRSVPs> list = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT * FROM EventRSVPs WHERE userID = "+ userID);
        while(rs.next()) {
            EventRSVPs r = new EventRSVPs();
            r.setRsvpID(rs.getInt("rsvpID"));
            r.setEventID(rs.getInt("eventID"));
            r.setUserID(rs.getInt("userID"));
            list.add(r);
        }
        return list;
    }

    public String getEventName(int eventID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT nameOfEvent FROM Event WHERE eventID = " + eventID);
        if(rs.next()) {
          return rs.getString("nameOfEvent");
        } else {
            return "";
        }
    }

    public int getEventID(String eventName) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT eventID FROM Event WHERE nameOfEvent = '" + eventName+"'");
        if(rs.next()) {
            return rs.getInt("eventID");
        } else {
            return -1;
        }
    }

    public int getFeedbackRecordNumber(int eventID) throws SQLException {
        s = con.createStatement();
        rs = s.executeQuery("SELECT COUNT(*) FROM EventFeedback WHERE eventID =" + eventID);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }

    public int getRatingFeedbackTally(int eventID) throws SQLException {
        List<Integer> ratings = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT rating FROM EventFeedback WHERE eventID =" +eventID);
        while (rs.next()) {
            ratings.add(rs.getInt("rating"));
        }
        int tally = 0;
        for (int i = 0; i < ratings.size(); i++) {
            tally += ratings.get(i);
        }
        return tally;
    }

    public int getRecommendationFeedbackTally(int eventID) throws SQLException {

        List<Integer> recommendations = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT recommendation FROM EventFeedback WHERE eventID =" +eventID);
        while (rs.next()) {
            recommendations.add(rs.getInt("recommendation"));
        }
        int tally = 0;
        for (int i = 0; i < recommendations.size(); i++) {
            tally += recommendations.get(i);
        }
        return tally;

    }

    public List<String> getEventComments(int eventID) throws SQLException {
        List<String> comments = new ArrayList<>();
        s = con.createStatement();
        rs = s.executeQuery("SELECT comment FROM EventFeedback WHERE eventID = " + eventID);
        while(rs.next()) {
            comments.add(rs.getString("comment"));
        }

        return comments;
    }






}
