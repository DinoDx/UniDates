package com.example.application.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@CssImport("./styles/views/main/mainviewlogin.css")
public class MainViewLogin extends AppLayout {

    private H1 viewTitle;

    public MainViewLogin(){
        setPrimarySection(Section.DRAWER);
        addToNavbar(true,createHeaderContent());
    }

    private Component createHeaderContent(){
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("layout");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        viewTitle = new H1("UniDates");
        layout.add(viewTitle);
        return layout;
    }
}
