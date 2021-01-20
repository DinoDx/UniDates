package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import org.dom4j.rule.Mode;

import java.io.ByteArrayInputStream;

public class infoModeratore extends Div {

    public infoModeratore(Moderatore moderatore){
        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(immagineMod(moderatore),infoMod(moderatore),segnalazioniMod(moderatore));
        add(horizontal);
    }

   public VerticalLayout immagineMod(Moderatore moderatore){
        VerticalLayout immagine = new VerticalLayout();
       Studente studente = (Studente) moderatore;
       StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
       Image image_profilo = new Image(resource,"");
       image_profilo.getStyle().set("width","250px");
       image_profilo.getStyle().set("height","250px");
       immagine.add(image_profilo);
       return immagine;
   }

   public VerticalLayout infoMod(Moderatore moderatore){
        VerticalLayout info = new VerticalLayout();
       Studente studente = (Studente) moderatore;
       Span nome = new Span("Nome : " + studente.getProfilo().getNome());
       Span cognome = new Span("Cognome : "+ studente.getProfilo().getCognome());
       Span email = new Span("Email : " + moderatore.getEmail());
       info.add(nome, cognome, email);
       return info;
   }

   public VerticalLayout segnalazioniMod(Moderatore moderatore){
        VerticalLayout segnalazioni = new VerticalLayout();
        Span segnalazioniRicevute = new Span("Segnalazioni Ricevute : " + moderatore.getSegnalazioneRicevute().size());
        Span segnalazioniRicevuteNV = new Span("Segnalazioni Ricevute Non Viste : ");
        Span ammonimentiInviati = new Span("Ammonimenti Inviati : " + moderatore.getAmmonimentoInviati().size());
        segnalazioni.add(segnalazioniRicevute, segnalazioniRicevuteNV, ammonimentiInviati);
        return segnalazioni;
   }

}
