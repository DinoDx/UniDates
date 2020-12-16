package com.example.application.views.main;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;


@CssImport("./styles/views/main/mainviewchat.css")
public class MainViewProfile extends AppLayout {

    private H1 viewTitle;

    public MainViewProfile(){
        setPrimarySection(Section.DRAWER);
        addToNavbar(true,createHeaderContent());
    }

    private Component createHeaderContent(){
        Button retry = new Button(new Icon(VaadinIcon.ARROW_BACKWARD));
        Anchor anchor = new Anchor("/home");
        anchor.add(retry);
        anchor.setId("retry");
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("layout_1");
        layout.getThemeList().set("dark", true);
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(anchor);
        viewTitle = new H1("Profilo Personale");
        layout.add(viewTitle);
        return layout;
    }

}
