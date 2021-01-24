package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.DTOs.NotificaDTO;
import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;

public class Ammonimento_Notifica extends Div {


    public Ammonimento_Notifica(NotificaDTO notifica){
        HorizontalLayout internal_layout = new HorizontalLayout();
        internal_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        internal_layout.setWidth("300px");
        internal_layout.setHeight("165px");

        VerticalLayout didascalie = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        didascalie.add(descrizione);

        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(notifica.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("200px");
        image.setHeight("200px");

        Button mostraAmmonimento = new Button("Apri");
        mostraAmmonimento.addClickListener(buttonClickEvent -> {
            Notification notification = new Notification();
           VerticalLayout verticalLayout = new VerticalLayout();
           Span motivazione = new Span("Motivo ammonimento : " + notifica.getAmmonimento().getMotivazione());
           Span dettagli = new Span("Dettagli : " + notifica.getAmmonimento().getDettagli());
           Button annulla = new Button("Chiudi");
           annulla.addClickListener(buttonClickEvent1 -> {
              notification.close();
           });
           verticalLayout.setWidth("400px");
           verticalLayout.setHeight("400px");
           verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
           verticalLayout.add(image,motivazione,dettagli,annulla);
           notification.add(verticalLayout);
           notification.setPosition(Notification.Position.MIDDLE);
           notification.open();
        });
        internal_layout.add(didascalie,mostraAmmonimento);
        add(internal_layout);
    }
}
