package com.example.application.views.visitors;

import com.example.application.data.Event;
import com.example.application.data.EventRSVPs;
import com.example.application.data.Users;
import com.example.application.services.DB;
import com.example.application.views.admin.adminView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.catalina.User;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@PageTitle("Visitors")
@Route(value = "visitors")
@Uses(Icon.class)
public class visitorsView extends Composite<VerticalLayout> implements HasUrlParameter<Integer> {
    Integer visitorsID;

    public visitorsView() throws SQLException {

    }

    public void setUserID(Integer visitorsID) {
        this.visitorsID = visitorsID;
    }

    public void addContents() throws SQLException {

        H3 myEvents = new H3("My Events");
        H3 userInfo = new H3("User Info");

        DB db = new DB();
        TabSheet tabSheet = new TabSheet();
        Div eventsTab = new Div();
        Div feedbackTab = new Div();
        Div profileTab = new Div();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, tabSheet);
        tabSheet.setWidth("100%");

        tabSheet.add("Events", eventsTab);
        tabSheet.add("Give Feedback", feedbackTab);
        tabSheet.add("Profile", profileTab);

        //events
        Grid<Event> events = new Grid<>(Event.class, false);
        events.addColumn(Event::getEventId).setHeader("Event ID");
        events.addColumn(Event::getNameOfEvent).setHeader("Name of Event");
        events.addColumn(Event::getDescription).setHeader("Description");
        events.addColumn(Event::getCategory).setHeader("Category");
        events.addColumn(Event::getEventStarTime).setHeader("Event Start Time");
        events.addColumn(Event::getEventEndTime).setHeader("Event End Time");
        events.addColumn(Event::getEventDate).setHeader("Event Date");
        events.addColumn(Event::getEventVenue).setHeader("Event Venue");
        events.addColumn(Event::getEventAttendanceLimit).setHeader("Attendance Limit");
        events.setItems(db.getEvents());

        TextArea info = new TextArea();
        info.setSizeFull();
        info.setReadOnly(true);

        Button reg = new Button("Register");
        reg.setVisible(false);

        events.addItemClickListener(event -> {
            info.setValue("Name of event: " + event.getItem().getNameOfEvent() + "\nDescription: " + event.getItem().getDescription() + "\nCategory: " + event.getItem().getCategory() + "\nTtime: " + event.getItem().getEventStarTime() + "\nDate: " + event.getItem().getEventDate() + "\nVenue: " + event.getItem().getEventVenue());
            reg.setVisible(true);
            reg.addClickListener(event1 -> {
                try {
                    if (db.getAttendanceCount(event.getItem().getEventId())< db.getAttendanceLimit(event.getItem().getEventId())) {

                        try {
                            db.createRSVP(visitorsID+event.getItem().getEventId(), event.getItem().getEventId(), visitorsID);
                            db.incrementAttendance(event.getItem().getEventId());
                        } catch (SQLException e) {
                            //Notification.show("You have already registered for this event.");
                        }
                    } else if(db.getAttendanceCount(event.getItem().getEventId()) == db.getAttendanceLimit(event.getItem().getEventId())) {
                        Notification.show("Attendance Limit for this event reached");
                    }
                } catch (SQLException e) {
                    Notification.show(e.getMessage());
                }
                reg.setVisible(false);
                info.clear();
            });
        });

        List<EventRSVPs> userRSVPs = db.getUserRSVPs(visitorsID);

        //feedback
        ComboBox<String> event = new ComboBox<>("Events Registered For");

        List<String> names = new ArrayList<>();

        for(int i = 0; i < userRSVPs.size(); i++) {
            names.add( db.getEventName(userRSVPs.get(i).getEventID()));
        }

        event.setItems(names);

        RadioButtonGroup rating = new RadioButtonGroup();
        RadioButtonGroup recommendation = new RadioButtonGroup();
        TextArea comment = new TextArea("Comment");

        rating.setItems("★", "★★", "★★★", "★★★★", "★★★★★");
        rating.setLabel("How well do you rate this Event?");
        rating.setSizeFull();
        rating.setWidthFull();

        recommendation.setItems("★", "★★", "★★★", "★★★★", "★★★★★");
        recommendation.setLabel("How likely are you to recommend this event?");
        recommendation.setSizeFull();
        recommendation.setWidthFull();

        comment.setSizeFull();
        comment.setWidthFull();

        Button submitFeedback = new Button("Submit");
        submitFeedback.addClickListener(event1 -> {
            if(rating.isEmpty()||recommendation.isEmpty()||comment.isEmpty()||event.isEmpty()) {
                Notification.show("One or more of the fields are empty");
            } else {
                int rat = 0, rec = 0;
                String com;
                if (rating.getValue() == "★") {
                    rat = 1;
                } else if (rating.getValue() == "**") {
                    rat = 2;
                } else if (rating.getValue() == "★★★") {
                    rat = 3;
                } else if (rating.getValue() == "★★★★") {
                    rat = 4;
                } else if (rating.getValue() == "★★★★★") {
                    rat = 5;
                }

                if (recommendation.getValue() == "★") {
                    rec = 1;
                } else if (recommendation.getValue() == "★★") {
                    rec = 2;
                } else if (recommendation.getValue() == "★★★") {
                    rec = 3;
                } else if (recommendation.getValue() == "★★★★") {
                    rec = 4;
                } else if (recommendation.getValue() == "★★★★★") {
                    rec = 5;
                }

                com = comment.getValue();

                try {
                    //Notification.show(""+db.getEventID(event.getValue()));
                    db.createFeedback(visitorsID + db.getEventID(event.getValue()), rat, rec, com, db.getEventID(event.getValue()), visitorsID);
                    rating.clear();
                    recommendation.clear();
                    comment.clear();
                    event.clear();
                    Notification.show("Feedback Saved");
                } catch (SQLException e) {
                    Notification.show("Feedback for this event exists.");
                }
            }
        });

        feedbackTab.add(event);
        feedbackTab.add(rating);
        feedbackTab.add(recommendation);
        feedbackTab.add(comment);
        feedbackTab.add(submitFeedback);

        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, rating);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        
        eventsTab.add(events);
        eventsTab.add(info);
        eventsTab.add(reg);

        profileTab.add(userInfo);
        H6 name = new H6("Name: " + db.getFirstName(visitorsID) + " " + db.getLastName(visitorsID));
        profileTab.add(name);
        H6 email = new H6("Email: "+db.getEmail(visitorsID));
        profileTab.add(email);
        H6 id = new H6("User ID: " + visitorsID);
        profileTab.add(id);

        profileTab.add(myEvents);
        for (int i = 0; i < userRSVPs.size(); i++) {
            profileTab.add(new H6(db.getEventName(userRSVPs.get(i).getEventID())) );
        }


        Button logout = new Button("Log out");
        logout.setSizeFull();
        profileTab.add(logout);
        logout.addClickListener(e -> {
            logout.getUI().ifPresent(ui -> {
                ui.navigate("");
            });
        });





        getContent().add(tabSheet);

    }

    public void setVisitorsID(Integer id) {
        visitorsID = id;
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        if (parameter != null) {
            try {
                visitorsView a = new visitorsView();
                a.setVisitorsID(parameter);
                a.addContents();
                getContent().add(a);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
