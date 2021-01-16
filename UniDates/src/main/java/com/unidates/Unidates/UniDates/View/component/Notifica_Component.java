package com.unidates.Unidates.UniDates.View.component;


import com.unidates.Unidates.UniDates.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
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
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class Notifica_Component extends Div {

    public Notifica_Component(Notifica notifica){
        VerticalLayout internal_card_due = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        internal_card_due.add(descrizione);
        if(notifica.getTipo_notifica() == Tipo_Notifica.MATCH){
            Button avvia_chat = new Button("Avvia chat!",new Icon(VaadinIcon.FIRE));
            internal_card_due.add(avvia_chat);
        }



        setSizeFull();
        setId("card-notifica");
        HorizontalLayout internal_card = new HorizontalLayout();

        internal_card.setWidth("300px");
        internal_card.setHeight("100px");
        internal_card.setAlignItems(FlexComponent.Alignment.CENTER);
        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(notifica.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("80px");
        image.getStyle().set("margin-left","2em");
        image.setHeight("80px");

        internal_card.add(image,internal_card_due);
        add(internal_card);
    }
}
