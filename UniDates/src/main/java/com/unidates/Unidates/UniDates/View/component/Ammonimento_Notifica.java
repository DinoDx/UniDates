package com.unidates.Unidates.UniDates.View.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Ammonimento_Notifica extends Div {

    public Ammonimento_Notifica(){
        HorizontalLayout internal_layout = new HorizontalLayout();
        internal_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        internal_layout.setWidth("300px");
        internal_layout.setHeight("100px");

        VerticalLayout didascalie = new VerticalLayout();
        H6 descrizione = new H6("Hai ricevuto un ammonimento.La tua foto è stata rimossa");
        didascalie.add(descrizione);

        Image image = new Image("https://randomuser.me/api/portraits/men/42.jpg","ciao");
        image.setWidth("80px");
        image.getStyle().set("margin-left","2em");
        image.setHeight("80px");

        internal_layout.add(didascalie,image);
        add(internal_layout);
    }
}
