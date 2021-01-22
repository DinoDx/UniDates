package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.Controller.GestioneInterazioniController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni.MatchService;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;

@Route(value = "ricercaprofilo",layout = MainView.class)
public class ProfiloView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    MatchService matchService;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    VerticalLayout tot;

    Studente studente;

    //utente in sessione
    Utente utente = SecurityUtils.getLoggedIn();
    Studente s = (Studente) utente;


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

    public ProfiloView(MatchService matchService, GestioneUtentiController gestioneUtentiController){
       this.matchService = matchService;
       this.gestioneUtentiController = gestioneUtentiController;

    }

    public VerticalLayout Page() {
        VerticalLayout allPage = new VerticalLayout();

        //padre
        HorizontalLayout horizontal = new HorizontalLayout();

        //layout immagine
        VerticalLayout image_layout = new VerticalLayout();
        StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image image_profilo = new Image(resource, "");
        image_profilo.getStyle().set("width","250px");
        image_profilo.getStyle().set("height","250px");
        image_layout.add(image_profilo);



        HorizontalLayout nome_cognome = new HorizontalLayout();
        nome = new Span(studente.getProfilo().getNome());
        cognome = new Span(studente.getProfilo().getCognome());
        nome_cognome.add(nome, cognome);

        topics = new Span("Topics:" + studente.getProfilo().getHobbyList().toString());
        interessi = new Span("Interessato a:" + studente.getProfilo().getInteressi().toString());

        HorizontalLayout città = new HorizontalLayout();
        residenza = new Span("Residenza:" + studente.getProfilo().getResidenza());
        nascita = new Span("Città di nascita:" + studente.getProfilo().getLuogoNascita());
        città.add(residenza, nascita);

        HorizontalLayout caratteristiche = new HorizontalLayout();
        colore_occhi = new Span("Colore occhi" + studente.getProfilo().getColore_occhi());
        colore_capelli = new Span("Colore capelli:" + studente.getProfilo().getColori_capelli());
        altezza = new Span("Altezza:" + studente.getProfilo().getAltezza());
        compleanno = new Span("Data di nascita:" + studente.getProfilo().getDataDiNascita());
        caratteristiche.add(colore_capelli, colore_occhi, altezza, compleanno);

        if(matchService.trovaMatch(s,studente) != null){
            VerticalLayout info_layout = new VerticalLayout();
            info_layout.add(nome_cognome, topics, interessi, città, caratteristiche);
            horizontal.add(image_layout, info_layout);
        }else {
            VerticalLayout noMatch = new VerticalLayout();
            noMatch.add(nome_cognome,topics,interessi);
            horizontal.add(image_layout,noMatch);
        }

        allPage.add(horizontal);
        return allPage;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        if(tot != null)
           remove(tot);
        studente = (Studente) gestioneUtentiController.trovaUtente(s);
        tot = Page();
        tot.setAlignItems(Alignment.CENTER);
        add(tot);
    }
}
