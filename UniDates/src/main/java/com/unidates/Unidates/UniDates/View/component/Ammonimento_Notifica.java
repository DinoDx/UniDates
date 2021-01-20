package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class Ammonimento_Notifica extends Div {

    public Ammonimento_Notifica(Notifica notifica){
        HorizontalLayout internal_layout = new HorizontalLayout();
        internal_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        internal_layout.setWidth("300px");
        internal_layout.setHeight("100px");

        VerticalLayout didascalie = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        didascalie.add(descrizione);

        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(notifica.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("80px");
        image.getStyle().set("margin-left","2em");
        image.setHeight("80px");

        internal_layout.add(didascalie,image);
        add(internal_layout);
    }
}
