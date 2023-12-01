package com.example.application.views.welcome;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.Image;


import com.example.application.services.DB;

import java.awt.*;
import java.sql.SQLException;

@PageTitle("Welcome")
@Route(value = "welcome")
@RouteAlias(value = "")
@Uses(Icon.class)
public class WelcomeView extends Composite<VerticalLayout> {

    DB db;
    public WelcomeView() {


        H1 h1 = new H1();
        H6 h6 = new H6();
        Text info = new Text("The Cape Town Arts Festival, powered by Ariva Arts Foundation, is a vibrant celebration of creativity and expression set against the stunning backdrop of Table Mountain and the historic Castle of Good Hope. Showcasing a tapestry of artistic excellence, it brings together local talents spanning painting, music, poetry, dance, and sculpture. Immerse yourself in this artistic journey through exhibitions, performances, workshops, and cultural events, where you’ll be captivated by emotive sculptures and diverse storytelling on canvases. Join us to honor creativity in all its forms and be enchanted by the transformative magic of the arts.");
        FormLayout formLayout2Col = new FormLayout();
        Button buttonPrimary = new Button();
        Button buttonSecondary = new Button();
        Image logo = new Image("images/LOGO-FOR-CTAF.png", "CTAF");
        logo.setWidth("150px");
        logo.setHeight("100px");
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        h1.setText("Welcome");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        h1.setWidth("max-content");
        h6.setText("Cape Town Arts Festival");
        getContent().setAlignSelf(FlexComponent.Alignment.CENTER, h6);
        h6.setWidth("max-content");
        formLayout2Col.setWidth("100%");
        buttonPrimary.setText("Sign Up");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Log In");
        buttonSecondary.setWidth("min-content");
        getContent().add(h1);
        getContent().add(logo);
        getContent().add(h6);
        getContent().add(info);
        getContent().add(formLayout2Col);
        formLayout2Col.add(buttonPrimary);
        formLayout2Col.add(buttonSecondary);




        buttonPrimary.addClickListener(e -> {
            buttonPrimary.getUI().ifPresent(ui ->
                    ui.navigate("signup"));

        });

        buttonSecondary.addClickListener(e -> {
            buttonSecondary.getUI().ifPresent(ui ->
                    ui.navigate("login"));
        });
    }
}
