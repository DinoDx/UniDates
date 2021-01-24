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
        internal_layout.setHeight("100px");

        VerticalLayout didascalie = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        didascalie.add(descrizione);

        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(notifica.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("80px");
        image.getStyle().set("margin-left","2em");
        image.setHeight("80px");

        Button mostraAmmonimento = new Button("Apri");
        mostraAmmonimento.addClickListener(buttonClickEvent -> {
            Notification notification = new Notification();
           VerticalLayout verticalLayout = new VerticalLayout();
           Span motivazione = new Span("Motivo ammonimento : " + notifica.getAmmonimento().getMotivazione());
           Span dettagli = new Span("Dettagli : " + notifica.getAmmonimento().getDettagli());
           verticalLayout.add(motivazione,dettagli);
           notification.add(verticalLayout);
           notification.setPosition(Notification.Position.MIDDLE);
           notification.setDuration(5000);
           notification.open();
        });
        internal_layout.add(didascalie,mostraAmmonimento,image);
        add(internal_layout);
    }
}
