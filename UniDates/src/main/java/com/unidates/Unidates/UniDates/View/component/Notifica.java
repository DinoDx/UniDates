package com.unidates.Unidates.UniDates.View.component;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@CssImport("./styles/views/component/card-notifica.css")
public class Notifica extends Div {

    public Notifica(){
        VerticalLayout internal_card_due = new VerticalLayout();
        H6 descrizione = new H6("Hai un match con ");
        Button avvia_chat = new Button("Avvia chat!",new Icon(VaadinIcon.FIRE));
        internal_card_due.add(descrizione,avvia_chat);

        setSizeFull();
        setId("card-notifica");
        HorizontalLayout internal_card = new HorizontalLayout();
        internal_card.setWidth("300px");
        internal_card.setHeight("100px");
        internal_card.setAlignItems(FlexComponent.Alignment.CENTER);
        Image image = new Image("https://randomuser.me/api/portraits/men/42.jpg","ciao");
        image.setWidth("80px");
        image.getStyle().set("margin-left","2em");
        image.setHeight("80px");

        internal_card.add(image,internal_card_due);
        add(internal_card);
    }
}
