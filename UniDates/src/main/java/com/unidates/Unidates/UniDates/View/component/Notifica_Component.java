package com.unidates.Unidates.UniDates.View.component;


import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.NotificaDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import java.io.ByteArrayInputStream;

public class Notifica_Component extends Div {

    NotificaDTO notifica;
    ProfiloDTO profilo;
    FotoDTO foto;
    GestioneProfiloController profiloController;
    GestioneUtentiController gestioneUtentiController;

    public Notifica_Component(NotificaDTO notifica,GestioneProfiloController controller){
        this.notifica = notifica;
        this.profiloController = controller;

        if(notifica.getTipo_notifica().equals(Tipo_Notifica.MATCH)) createNotificaMatch();
        else createNotificaAmmonimento();
    }
    public void createNotificaMatch(){
        VerticalLayout internal_card_due = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        internal_card_due.add(descrizione, new H6(notifica.getCreationTime().toString()));


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
        Button pulsante_email = new Button(new Icon(VaadinIcon.FIRE));
        pulsante_email.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate("ricercaprofilo/"+ notifica.getEmailToMatchWith());
        });


        internal_card.add(image,internal_card_due,pulsante_email);
        add(internal_card);
    }

    Notification notification = new Notification();

    public void createNotificaAmmonimento(){
        HorizontalLayout internal_layout = new HorizontalLayout();
        internal_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        internal_layout.setWidth("300px");
        internal_layout.setHeight("165px");

        VerticalLayout didascalie = new VerticalLayout();
        H6 descrizione = new H6(notifica.getTestoNotifica());
        didascalie.add(descrizione, new H6(notifica.getCreationTime().toString()));

        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(notifica.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.setWidth("200px");
        image.setHeight("200px");

        Button mostraAmmonimento = new Button("Apri");
        mostraAmmonimento.addClickListener(buttonClickEvent -> {
            if(!notification.isOpened()) {
                notification = new Notification();
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
                verticalLayout.add(image, motivazione, dettagli, annulla);
                notification.add(verticalLayout);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.open();
            }
        });
        internal_layout.add(didascalie,mostraAmmonimento);
        add(internal_layout);
    }



    public Notification MatchEmail(){
        Notification email = new Notification();
        email.setPosition(Notification.Position.MIDDLE);
        email.setDuration(5000);
        VerticalLayout descrizione = new VerticalLayout();
        descrizione.setAlignItems(FlexComponent.Alignment.CENTER);

        foto = notifica.getFoto();
        profilo = profiloController.trovaProfilo(foto.getProfiloId());

        Span testo = new Span("Hai un match con:"+ profilo.getNome());
        Span testo_due = new Span("Email:"+ notifica.getEmailToMatchWith());
        Span testo_tre = new Span("Cerca l'utente!");
        descrizione.add(testo,testo_due,testo_tre);
        email.add(descrizione);
        return email;
    }
}
