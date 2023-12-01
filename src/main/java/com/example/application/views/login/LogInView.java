package com.example.application.views.login;

import com.example.application.services.DB;
import com.example.application.views.admin.adminView;
import com.example.application.views.visitors.visitorsView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.*;

import java.sql.SQLException;

@PageTitle("Log In")
@Route(value = "login")
@Uses(Icon.class)
public class LogInView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    public LogInView() throws SQLException {

        H1 h1 = new H1();
        DB db = new DB();
        FormLayout formLayout2Col = new FormLayout();
        IntegerField id = new IntegerField();
        id.setMax(9);

        PasswordField passwordField = new PasswordField();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        h1.setText("Log In");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        h1.setWidth("max-content");
        formLayout2Col.setWidth("100%");
        id.setLabel("ID");
        id.setWidth("min-content");
        passwordField.setLabel("Password");
        passwordField.setWidth("min-content");
        buttonPrimary.setText("Log In");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Back");
        buttonSecondary.setWidth("min-content");
        getContent().add(h1);
        getContent().add(formLayout2Col);
        formLayout2Col.add(id);
        formLayout2Col.add(passwordField);
        formLayout2Col.add(buttonPrimary);
        formLayout2Col.add(buttonSecondary);

        buttonPrimary.addClickListener(e -> {
            int userID = id.getValue();
            try {
                if(db.userIDExists(userID)) {
                    String enteredPassword = passwordField.getValue();
                    String userPassword = db.getPassword(userID);
                    if (enteredPassword.equals(userPassword)) {
                        Integer a = userID;

                        if(db.getRole(userID).equals("admin")) {
                            buttonPrimary.getUI().ifPresent(ui ->
                                    ui.navigate(adminView.class, a));


                        } else if (db.getRole(userID).equals("visitor")) {
                            buttonPrimary.getUI().ifPresent(ui ->
                                    ui.navigate(visitorsView.class, a));
                        } else {
                            Notification.show("Unknown role;");
                        }


                    } else {
                        Notification.show("Password is incorrect.");
                    }
                } else {
                    Notification.show("ID does not exist");
                }
            } catch (SQLException ex) {
                Notification.show(ex.getMessage());
            }
        });
        buttonSecondary.addClickListener(e -> {
            buttonPrimary.getUI().ifPresent(ui ->
                    ui.navigate(""));
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }
}
