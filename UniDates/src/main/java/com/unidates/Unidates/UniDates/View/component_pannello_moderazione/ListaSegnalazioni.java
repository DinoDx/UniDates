package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.vaadin.event.MouseEvents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class ListaSegnalazioni extends Div implements MouseEvents.ClickListener {

    public ListaSegnalazioni(Moderatore moderatore){

        VerticalLayout vertical = new VerticalLayout();
        for(Segnalazione s : moderatore.getSegnalazioneRicevute()){
            vertical.add(segnalazione(s));
        }
        add(vertical);
    }

    public HorizontalLayout segnalazione(Segnalazione segnalazione){
        HorizontalLayout horizontal = new HorizontalLayout();
        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");
        Span testo = new Span("Hai ricevuto una segnalazione per una foto di : " + segnalazione.getFoto().getProfilo().getNome() + segnalazione.getFoto().getProfilo().getCognome() );
        horizontal.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontal.add(image, testo);
        return horizontal;
    }

    @Override
    public void click(MouseEvents.ClickEvent clickEvent) {

    }
}
