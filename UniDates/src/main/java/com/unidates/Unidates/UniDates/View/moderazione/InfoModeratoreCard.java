package com.unidates.Unidates.UniDates.View.moderazione;

import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class InfoModeratoreCard extends Div {

    ModeratoreDTO moderatore;
    public InfoModeratoreCard(ModeratoreDTO mod){
        moderatore = mod;
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(immagineMod(),infoMod(),segnalazioniMod());
        add(horizontal);
    }

   public VerticalLayout immagineMod(){
        VerticalLayout immagine = new VerticalLayout();
       StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(moderatore.getProfilo().getFotoProfilo().getImg()));
       Image image_profilo = new Image(resource,"");
       image_profilo.getStyle().set("width","250px");
       image_profilo.getStyle().set("height","250px");
       immagine.add(image_profilo);
       return immagine;
   }

   public VerticalLayout infoMod(){
        VerticalLayout info = new VerticalLayout();
       Span nome = new Span("Nome : " + moderatore.getProfilo().getNome());
       Span cognome = new Span("Cognome : "+ moderatore.getProfilo().getCognome());
       Span email = new Span("Email : " + moderatore.getEmail());
       info.add(nome, cognome, email);
       return info;
   }

   public VerticalLayout segnalazioniMod(){
        VerticalLayout segnalazioni = new VerticalLayout();
        Span segnalazioniRicevute = new Span("Segnalazioni Ricevute : " + moderatore.getSegnalazioneRicevute().size());
        Span ammonimentiInviati = new Span("Ammonimenti Inviati : " + moderatore.getAmmonimentoInviati().size());
        segnalazioni.add(segnalazioniRicevute, ammonimentiInviati);
        return segnalazioni;
   }

}
