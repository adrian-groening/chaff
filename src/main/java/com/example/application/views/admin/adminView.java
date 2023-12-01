package com.example.application.views.admin;

import com.example.application.data.ArchivedEvents;
import com.example.application.data.Event;
import com.example.application.data.EventRSVPs;
import com.example.application.data.Users;
import com.example.application.services.DB;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;


import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@PageTitle("administrator")
@Route(value = "administrator")
@Uses(Icon.class)
public class adminView extends Composite<VerticalLayout> implements HasUrlParameter<Integer> {
    Integer adminID;
    IntegerField eventID = new IntegerField("Event ID");
    TextField nameOfEvent = new TextField("Name of Event");
    TextField description = new TextField("Description");
    ComboBox<String> category = new ComboBox<>("Category");
    TimePicker eventStarTime = new TimePicker("Event Start Time");
    TimePicker eventEndTime = new TimePicker("Event End Time");
    DatePicker eventDate = new DatePicker("Event Date");
    TextField eventVenue = new TextField("Event Venue");
    IntegerField attendanceLimit = new IntegerField("Event Attendance Limit");
    IntegerField attendanceCount = new IntegerField("AttendanceCount");


    public adminView() throws SQLException {

    }

    public void clear() {
        eventID.clear();
        nameOfEvent.clear();
        description.clear();
        category.clear();
        eventStarTime.clear();
        eventEndTime.clear();
        eventDate.clear();
        eventVenue.clear();
        attendanceLimit.clear();
        attendanceLimit.clear();
    }

     public void setAdminID(Integer adminID) {
        this.adminID = adminID;
     }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        if (parameter != null) {
            try {
                adminView a = new adminView();
                a.setAdminID(parameter);
                a.addContents();
                getContent().add(a);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addContents() throws SQLException {
        H1 archivedEventsTitle = new H1("Archived Events");
        TabSheet tabSheet = new TabSheet();
        Div manageEvents = new Div();
        Div visitors = new Div();
        Div attendees = new Div();
        Div dashboard = new Div();
        Div profile = new Div();

        DB db = new DB();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, tabSheet);
        tabSheet.setWidth("100%");

        tabSheet.add("Dashboard", dashboard);
        tabSheet.add("Events", manageEvents);
        tabSheet.add("Visitors", visitors);
        tabSheet.add("RSVP", attendees);
        tabSheet.add("Profile", profile);

        profile.add("Name: " + db.getFirstName(adminID) + " " + db.getLastName(adminID));
        Button logout = new Button("Log out");
        logout.setSizeFull();
        profile.add(logout);
        logout.addClickListener(event -> {
            logout.getUI().ifPresent(ui -> {
                ui.navigate("");
            });
        });

        //manage events tabsheet
        category.setItems("Music", "Arts & Crafts", "Food", "Wellness", "Dance");
        attendanceCount.setReadOnly(true);
        attendanceLimit.setStepButtonsVisible(true);
        FormLayout formLayout2Col = new FormLayout();
        formLayout2Col.add(eventID, nameOfEvent, description, category, eventStarTime, eventEndTime, eventDate, eventVenue, attendanceLimit, attendanceCount);

        Button create = new Button("Create");
        Button edit = new Button("Edit");
        Button delete = new Button("Delete");
        Button archive = new Button("Archive");

        formLayout2Col.add(create, edit, delete, archive);

        Grid<ArchivedEvents> archivedEvents = new Grid<>(ArchivedEvents.class, true);
        archivedEvents.addColumn(ArchivedEvents::getArchivedEventID).setHeader("Archived Event ID");
        archivedEvents.addColumn(ArchivedEvents::getEventID).setHeader("Event ID");
        archivedEvents.addColumn(ArchivedEvents::getEventName).setHeader("EventName");
        archivedEvents.addColumn(ArchivedEvents::getAttendanceCount).setHeader("Attendance Count");
        archivedEvents.setItems(db.getArchivedEvents());

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
        events.addColumn(Event::getEventAttendanceCount).setHeader("Attendance Count");

        events.setItems(db.getEvents());

        events.addItemClickListener(event -> {
            Notification.show(event.getItem().getNameOfEvent());
            eventID.setValue(event.getItem().getEventId());
            nameOfEvent.setValue(event.getItem().getNameOfEvent());
            description.setValue(event.getItem().getDescription());
            category.setValue(event.getItem().getCategory());
            eventStarTime.setValue(LocalTime.parse(event.getItem().getEventStarTime()));
            eventEndTime.setValue(LocalTime.parse(event.getItem().getEventEndTime()));
            //eventDate.setValue(LocalDate.parse(event.getItem().getEventDate()));
            eventVenue.setValue(event.getItem().getEventVenue());
            attendanceLimit.setValue(event.getItem().getEventAttendanceLimit());
            attendanceCount.setValue(event.getItem().getEventAttendanceCount());
        });

        create.addClickListener(event -> {
            if(eventID.isEmpty()||nameOfEvent.isEmpty()||category.isEmpty()||eventStarTime.isEmpty()||eventEndTime.isEmpty()||eventDate.isEmpty()||attendanceLimit.isEmpty()) {
                Notification.show("Fill all relevant fields.");
            } else {
                try {
                    db.createEvent(Integer.valueOf(eventID.getValue()), nameOfEvent.getValue(), description.getValue(), category.getValue(), eventStarTime.getValue().toString(), eventEndTime.getValue().toString(), eventDate.getValue().toString(), eventVenue.getValue(), attendanceLimit.getValue(), 0);
                    Notification.show("Event Created");
                    clear();
                    create.getUI().ifPresent(ui -> {
                        ui.navigate("");
                        ui.navigate(adminView.class, adminID);
                    });
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        edit.addClickListener(event -> {
            if(eventID.isEmpty()||nameOfEvent.isEmpty()||category.isEmpty()||eventStarTime.isEmpty()||eventEndTime.isEmpty()||eventDate.isEmpty()||attendanceLimit.isEmpty()) {
                Notification.show("Populate the fields by clicking on a grid row");
            } else {
                try {
                    if(db.eventIDExists(Integer.valueOf(eventID.getValue()))) {
                        db.updateEventName(Integer.valueOf(eventID.getValue()), nameOfEvent.getValue());
                        db.updateEventDescription(Integer.valueOf(eventID.getValue()), description.getValue());
                        db.updateEventCategory(Integer.valueOf(eventID.getValue()), category.getValue());
                        db.updateEventStarTime(Integer.valueOf(eventID.getValue()), eventStarTime.getValue().toString());
                        db.updateEventEndTime(Integer.valueOf(eventID.getValue()), eventEndTime.getValue().toString());
                        db.updateEventDate(Integer.valueOf(eventID.getValue()), eventDate.getValue().toString());
                        db.updateEventVenue(Integer.valueOf(eventID.getValue()), eventVenue.getValue());
                        db.updateEventAttendanceLimit(Integer.valueOf(eventID.getValue()), String.valueOf(Integer.valueOf(attendanceLimit.getValue())));
                        Notification.show("Updated");
                        edit.getUI().ifPresent(ui -> {
                            ui.navigate("");
                            ui.navigate(adminView.class, adminID);
                        });
                    } else {
                        Notification.show("ID does not exist");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        archive.addClickListener(event -> {
            if(eventID.isEmpty()) {
                Notification.show("Event ID is empty");
            } else {
                try {
                    db.archiveEvent(Integer.valueOf(eventID.getValue()+Integer.valueOf(attendanceCount.getValue())), Integer.valueOf(eventID.getValue()), nameOfEvent.getValue(), LocalDate.now().toString(), Integer.valueOf(attendanceCount.getValue()));
                    db.deleteEvent(Integer.valueOf(eventID.getValue()));
                    Notification.show("Archived");
                    clear();
                    archive.getUI().ifPresent(ui -> {
                        ui.navigate("");
                        ui.navigate(adminView.class, adminID);
                    });
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        delete.addClickListener(event -> {
            if(eventID.isEmpty()) {
                Notification.show("No event ID to delete");
            } else {
                try {
                    db.deleteEvent(Integer.valueOf(eventID.getValue()));
                    Notification.show("Event Deleted; Reload Page.");
                    clear();
                    delete.getUI().ifPresent(ui -> {
                        ui.navigate("");
                        ui.navigate(adminView.class, adminID);
                    });
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //visitors tabsheet
        Grid<Users> users = new Grid<>(Users.class, false);
        users.addColumn(Users::getUserID).setHeader("User ID");
        users.addColumn(Users::getRole).setHeader("Role");
        users.addColumn(Users::getFirstName).setHeader("First Name");
        users.addColumn(Users::getLastName).setHeader("Last Name");
        users.addColumn(Users::getGender).setHeader("Gender");
        users.addColumn(Users::getDateOfBirth).setHeader("Date of Birth");
        users.addColumn(Users::getEmail).setHeader("Email");
        users.addColumn(Users::getPhoneNumber).setHeader("Phone");
        users.addColumn(Users::getPassword).setHeader("Password");
        users.addColumn(Users::getAddress).setHeader("address");
        users.setItems(db.getUsers());
        visitors.add(users);

        //RSVPs Tabsheet
        Grid<EventRSVPs> rsvps = new Grid<>(EventRSVPs.class, false);
        rsvps.addColumn(EventRSVPs::getRsvpID).setHeader("Event RSVP ID");
        rsvps.addColumn(EventRSVPs::getEventID).setHeader("Event ID");
        rsvps.addColumn(EventRSVPs::getUserID).setHeader("User ID");
        rsvps.setItems(db.getRSVPs());
        attendees.add(rsvps);

        for (int i = 0; i < db.getEvents().size(); i++) {
            Event a = db.getEvents().get(i);
            Details d = new Details();
            d.setSummaryText(a.getNameOfEvent());
            attendees.add(d);

            for (int j = 0; j < db.getRSVPs().size(); j++) {
                EventRSVPs r = db.getRSVPs().get(j);
                if(a.getEventId() == r.getEventID()) {
                    Span name = new Span(r.getUserID()+ ": " + db.getFirstName(r.getUserID())+" "+db.getLastName(r.getUserID()) );
                    d.add(new H6(name));
                }
                attendees.add(d);
            }
        }


        //dashboard
        H1 festivalTitle = new H1("Festival");

        Grid<Users> dashVisitors = new Grid<>(Users.class, false);
        dashVisitors.addColumn(Users::getFirstName).setHeader("First Name");
        dashVisitors.addColumn(Users::getLastName).setHeader("Last Name");
        dashVisitors.addColumn(Users::getGender).setHeader("Gender");
        dashVisitors.addColumn(Users::getDateOfBirth).setHeader("Date of Birth");
        dashVisitors.addColumn(Users::getEmail).setHeader("Email");
        dashVisitors.setItems(db.dashUsers());

        Grid<Event> dashEvents = new Grid<>(Event.class, false);
        dashEvents.addColumn(Event::getNameOfEvent).setHeader("Name of Event");
        dashEvents.addColumn(Event::getCategory).setHeader("Category");
        dashEvents.addColumn(Event::getEventDate).setHeader("Event Date");
        dashEvents.addColumn(Event::getEventAttendanceCount).setHeader("Attendance");
        dashEvents.addColumn(Event::getEventAttendanceLimit).setHeader("Attendance Limit");
        dashEvents.setItems(db.dashEvents());

        FormLayout formLayout2Col2 = new FormLayout();
        formLayout2Col2.add(dashVisitors);
        formLayout2Col2.add(dashEvents);

        dashboard.add(festivalTitle);
        dashboard.add(formLayout2Col2);

        for (int i = 0; i < db.getEvents().size(); i++) {
            Event a = db.getEvents().get(i);
            int recordNo = db.getFeedbackRecordNumber(a.getEventId());

            Details d = new Details();
            d.setSummaryText(a.getNameOfEvent());
            dashboard.add(d);

            for (int j = 0; j < db.getRSVPs().size(); j++) {
                EventRSVPs r = db.getRSVPs().get(j);
                if(a.getEventId() == r.getEventID()) {
                    Span name = new Span(r.getUserID()+ ": " + db.getFirstName(r.getUserID())+" "+db.getLastName(r.getUserID()) );
                    d.add(new H6(name));
                }
                dashboard.add(d);
            }

            if (db.getFeedbackRecordNumber(a.getEventId())>0) {
                int rateAverage = db.getRatingFeedbackTally(a.getEventId()) / db.getFeedbackRecordNumber(a.getEventId());
                int recomAverage = db.getRecommendationFeedbackTally(a.getEventId()) / db.getFeedbackRecordNumber(a.getEventId());

                H1 rate = new H1("Average rating: " + rateAverage + "/5 stars (★) ");
                H1 recom = new H1("Average recommendation: " + recomAverage + "/5 stars (★) ");

                d.add(rate, recom);
            }

            List<String> comments = db.getEventComments(a.getEventId());
            H3 commentsHead = new H3("Comments");
            d.add(commentsHead);
            for (int j = 0; j < comments.size(); j++) {
                H6 com = new H6("'"+comments.get(j) +"'");
                d.add(com);
            }
        }

        manageEvents.add(events);
        manageEvents.add(formLayout2Col);
        manageEvents.add(archivedEventsTitle);
        manageEvents.add(archivedEvents);
        getContent().add(tabSheet);
        getContent().add();

    }
}
