package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidRegistrationFormatException;
import com.unidates.Unidates.UniDates.Exception.UserNotFoundException;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;

@Route(value = "ricercaprofilo",layout = MainView.class)
public class ProfiloView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    GestioneInterazioniController gestioneInterazioniController;
    @Autowired
    GestioneUtentiController gestioneUtentiController;

    VerticalLayout tot;
    StudenteDTO daCercare;
    StudenteDTO inSessione;

    Span nome;
    Span cognome = new Span();
    Span topics = new Span();
    Span interessi = new Span();
    Span residenza = new Span();
    Span nascita = new Span();
    Span colore_occhi = new Span();
    Span colore_capelli = new Span();
    Span altezza = new Span();
    Span compleanno = new Span();
    Span numero = new Span();
    Span instagram = new Span();

    public ProfiloView(){
    }

    public VerticalLayout Page() {
        VerticalLayout allPage = new VerticalLayout();

        //padre
        HorizontalLayout horizontal = new HorizontalLayout();

        //layout immagine
        VerticalLayout image_layout = new VerticalLayout();
        StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(daCercare.getProfilo().getListaFoto().get(0).getImg()));
        Image image_profilo = new Image(resource, "");
        image_profilo.getStyle().set("width","250px");
        image_profilo.getStyle().set("height","250px");
        Button bloccaSblocca;
        if(inSessione.getListaBloccatiEmail().contains(daCercare.getEmail())){
            bloccaSblocca = new Button("Sblocca");
            bloccaSblocca.addClickListener(buttonClickEvent -> {
                gestioneUtentiController.sbloccaStudente(inSessione.getEmail(), daCercare.getEmail());
                UI.getCurrent().getPage().reload();
            });
        }else {
            bloccaSblocca = new Button("Blocca");
            bloccaSblocca.addClickListener(buttonClickEvent -> {
                gestioneUtentiController.bloccaStudente(inSessione.getEmail(), daCercare.getEmail());
                UI.getCurrent().getPage().reload();
            });
        }
        image_layout.add(image_profilo,bloccaSblocca);




        HorizontalLayout nome_cognome = new HorizontalLayout();
        nome = new Span(daCercare.getProfilo().getNome());
        cognome = new Span(daCercare.getProfilo().getCognome());
        nome_cognome.add(nome, cognome);

        topics = new Span("Topics:" + daCercare.getProfilo().getHobbyList().toString());
        interessi = new Span("Interessato a:" + daCercare.getProfilo().getInteressi().toString());

        HorizontalLayout città = new HorizontalLayout();
        residenza = new Span("Residenza:" + daCercare.getProfilo().getResidenza());
        nascita = new Span("Città di nascita:" + daCercare.getProfilo().getLuogoNascita());
        città.add(residenza, nascita);

        HorizontalLayout caratteristiche = new HorizontalLayout();
        colore_occhi = new Span("Colore occhi" + daCercare.getProfilo().getColore_occhi());
        colore_capelli = new Span("Colore capelli:" + daCercare.getProfilo().getColori_capelli());
        altezza = new Span("Altezza:" + daCercare.getProfilo().getAltezza());
        compleanno = new Span("Data di nascita:" + daCercare.getProfilo().getDataDiNascita());
        caratteristiche.add(colore_capelli, colore_occhi, altezza, compleanno);

        HorizontalLayout contatti = new HorizontalLayout();
        numero = new Span("Numero di cellulare: " + daCercare.getProfilo().getNumeroTelefono());
        instagram = new Span("Contatto instagram: " + daCercare.getProfilo().getNickInstagram());
        contatti.add(numero,instagram);


        HorizontalLayout listaFoto = new HorizontalLayout();

        for(FotoDTO f : daCercare.getProfilo().getListaFoto()) {
            StreamResource resource2 = new StreamResource("fotoprofilo", () -> new ByteArrayInputStream(f.getImg()));
            Image img = new Image(resource2, "");
            img.getStyle().set("width", "200px");
            img.getStyle().set("height", "200px");
            listaFoto.add(img);
        }


        if(gestioneInterazioniController.isValidMatch(inSessione.getEmail(), daCercare.getEmail())){
            VerticalLayout info_layout = new VerticalLayout();
            info_layout.add(nome_cognome, topics, interessi, città,contatti, caratteristiche);
            horizontal.add(image_layout, info_layout);
            allPage.add(horizontal,listaFoto);
        }else {
            VerticalLayout noMatch = new VerticalLayout();
            noMatch.add(nome_cognome,topics,interessi);
            horizontal.add(image_layout,noMatch);
            allPage.add(horizontal);
        }
        return allPage;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try{
            daCercare = gestioneUtentiController.trovaStudente(s);
        }
        catch(UserNotFoundException e){
            Notification erroreRicerca = new Notification("Utente non trovato!",5000, Notification.Position.MIDDLE);
            erroreRicerca.open();
        }
        catch(InvalidRegistrationFormatException e){

        }

        inSessione = gestioneUtentiController.utenteInSessione();


        if (tot != null)
            remove(tot);
        tot = Page();
        tot.setAlignItems(Alignment.CENTER);
        add(tot);
    }

}
