package com.unidates.Unidates.UniDates.View.component;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;

@Route(value = "ricercaprofilo",layout = MainView.class)
public class ProfiloView extends VerticalLayout {


    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);


    String email;
    Studente studente = (Studente) httpSession.getAttribute("studente");


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

    public ProfiloView(){
        httpSession.removeAttribute("studente");
        VerticalLayout tot = Page();
        tot.setAlignItems(Alignment.CENTER);
        add(tot);
    }

    public VerticalLayout Page() {
        VerticalLayout allPage = new VerticalLayout();

        //padre
        HorizontalLayout horizontal = new HorizontalLayout();
        VerticalLayout image_layout = new VerticalLayout();
        StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image image_profilo = new Image(resource, "");
        image_profilo.getStyle().set("width","250px");
        image_profilo.getStyle().set("height","250px");
        image_layout.add(image_profilo);

        VerticalLayout info_layout = new VerticalLayout();

        HorizontalLayout nome_cognome = new HorizontalLayout();

        nome = new Span (studente.getProfilo().getNome());
        cognome.setText(studente.getProfilo().getCognome());
        nome_cognome.add(nome, cognome);

        topics.setText("Topics:" + studente.getProfilo().getHobbyList().toString());
        interessi.setText("Interessato a:" + studente.getProfilo().getInteressi().toString());


        HorizontalLayout città = new HorizontalLayout();
        residenza.setText("Residenza:" + studente.getProfilo().getResidenza());
        nascita.setText("Città di nascita:" + studente.getProfilo().getLuogoNascita());
        città.add(residenza, nascita);



        HorizontalLayout caratteristiche = new HorizontalLayout();
        colore_occhi.setText("Colore occhi" + studente.getProfilo().getColore_occhi());
        colore_capelli.setText("Colore capelli:" + studente.getProfilo().getColori_capelli());
        altezza.setText("Altezza:" + studente.getProfilo().getAltezza());
        compleanno.setText("Data di nascita:" + studente.getProfilo().getDataDiNascita());
        caratteristiche.add(colore_capelli, colore_occhi, altezza, compleanno);

        info_layout.add(nome_cognome, topics, interessi, città, caratteristiche);
        horizontal.add(image_layout, info_layout);
        allPage.add(horizontal);
        return allPage;
    }







}
