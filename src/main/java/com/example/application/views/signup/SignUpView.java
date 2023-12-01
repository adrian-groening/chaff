package com.example.application.views.signup;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.services.DB;

import java.sql.SQLException;

@PageTitle("Sign Up")
@Route(value = "signup")
@Uses(Icon.class)
public class SignUpView extends Composite<VerticalLayout> {

    public SignUpView() throws SQLException {

        DB db = new DB();

        H1 h1 = new H1();
        FormLayout formLayout2Col = new FormLayout();
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField userID = new TextField();
        DatePicker dateOfBirth = new DatePicker();
        TextField email = new TextField();
        TextField phoneNumber = new TextField();
        TextField address = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField2 = new PasswordField();

        ComboBox<String> gender = new ComboBox<>("Gender");
        gender.setItems("Male", "Female");

        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        h1.setText("Sign Up");
        h1.setWidth("max-content");
        formLayout2Col.setWidth("100%");
        firstName.setLabel("First name");
        firstName.setWidth("min-content");
        lastName.setLabel("Last name");
        lastName.setWidth("min-content");
        userID.setLabel("ID");
        userID.setWidth("min-content");
        dateOfBirth.setLabel("Date of birth");
        dateOfBirth.setWidth("min-content");
        email.setLabel("E-mail");
        email.setWidth("min-content");
        phoneNumber.setLabel("Phone number");
        phoneNumber.setWidth("min-content");
        address.setLabel("Address");
        address.setWidth("min-content");
        passwordField.setLabel("Password");
        passwordField2.setLabel("Confirm Password");
        passwordField.setWidth("min-content");
        passwordField2.setWidth("min-content");
        buttonPrimary.setText("Sign Up");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Back");
        buttonSecondary.setWidth("min-content");
        getContent().add(h1);
        getContent().add(formLayout2Col);
        formLayout2Col.add(firstName);
        formLayout2Col.add(lastName);
        formLayout2Col.add(userID);
        formLayout2Col.add(dateOfBirth);
        formLayout2Col.add(email);
        formLayout2Col.add(gender);
        formLayout2Col.add(phoneNumber);
        formLayout2Col.add(address);
        formLayout2Col.add(passwordField);
        formLayout2Col.add(passwordField2);
        formLayout2Col.add(buttonPrimary);
        formLayout2Col.add(buttonSecondary);

        firstName.setRequired(true);
        lastName.setRequired(true);
        userID.setRequired(true);
        dateOfBirth.setRequired(true);
        email.setRequired(true);
        gender.setRequired(true);
        phoneNumber.setRequired(true);
        address.setRequired(true);
        passwordField.setRequired(true);
        passwordField2.setRequired(true);

        userID.setMaxLength(9);
        firstName.setMaxLength(20);
        lastName.setMaxLength(20);
        email.setMaxLength(100);
        phoneNumber.setMaxLength(20);
        passwordField.setMaxLength(50);
        address.setMaxLength(50);

        userID.setPattern("^\\d+$");




        buttonPrimary.addClickListener(e -> {



            if(firstName.isEmpty()||lastName.isEmpty()||userID.isEmpty()||dateOfBirth.isEmpty()||email.isEmpty()||gender.isEmpty()||phoneNumber.isEmpty()||address.isEmpty()||passwordField.isEmpty()||passwordField2.isEmpty()) {
                Notification.show("One or more of the fields are empty or not filled correctly");
            } else if(userID.isInvalid()) {
                Notification.show("User ID is invalid");
            } else {
                int id = Integer.parseInt(userID.getValue());
                try {
                    if(db.userIDExists(id)) {
                        Notification.show("ID already exists");
                    } else {
                        if (passwordField.getValue().equals(passwordField2.getValue())) {
                            if (passwordField.getValue().length()>=6) {

                                db.createVisitor(id, firstName.getValue(), lastName.getValue(), gender.getValue(), dateOfBirth.getValue().toString(), email.getValue(), phoneNumber.getValue(), passwordField.getValue(), address.getValue());
                                Notification.show("User signed up");
                                buttonPrimary.getUI().ifPresent(ui ->
                                        ui.navigate(""));


                            } else {
                                Notification.show("Password should be at least 6 characters");
                            }
                        } else {
                            Notification.show("Passwords dont match");
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonSecondary.addClickListener(e -> {
            buttonSecondary.getUI().ifPresent(ui ->
                    ui.navigate("welcome"));
        });
    }
}
