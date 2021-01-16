package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class ProfiloView extends VerticalLayout {

    public ProfiloView(Utente utente){
        Div tot = Page(utente);
        add(tot);
    }

    public Div Page(Utente utente){
        Div allPage = new Div();
        Studente studente = (Studente) utente;
        //padre
        HorizontalLayout horizontal = new HorizontalLayout();

        VerticalLayout image_layout = new VerticalLayout();
        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image image_profilo = new Image(resource,"");
        image_layout.add(image_profilo);

        VerticalLayout info_layout = new VerticalLayout();

        HorizontalLayout nome_cognome = new HorizontalLayout();
        Span nome = new Span(studente.getProfilo().getNome());
        Span cogome = new Span(studente.getProfilo().getCognome());
        nome_cognome.add(nome,cogome);

        Span topics = new Span("Topics:"+studente.getProfilo().getHobbyList().toString());
        Span interessi = new Span("Interessato a:"+studente.getProfilo().getInteressi().toString());

        HorizontalLayout città = new HorizontalLayout();
        Span residenza = new Span("Residenza:"+studente.getProfilo().getResidenza());
        Span nascita = new Span("Città di nascita:"+studente.getProfilo().getLuogoNascita());
        città.add(residenza,nascita);

        HorizontalLayout caratteristiche = new HorizontalLayout();
        Span colore_occhi = new Span("Colore occhi"+studente.getProfilo().getColore_occhi());
        Span colore_capelli = new Span("Colore capelli:"+studente.getProfilo().getColori_capelli());
        Span altezza = new Span("Altezza:"+studente.getProfilo().getAltezza());
        Span compleanno = new Span("Data di nascita:"+studente.getProfilo().getDataDiNascita());
        caratteristiche.add(colore_capelli,colore_occhi,altezza,compleanno);


        info_layout.add(nome_cognome,topics,interessi,città,caratteristiche);

        horizontal.add(image_layout,info_layout);
        return allPage;
    }

}
