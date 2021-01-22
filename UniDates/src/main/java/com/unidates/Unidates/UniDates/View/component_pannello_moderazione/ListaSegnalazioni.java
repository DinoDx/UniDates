package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.CommunityManager;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.dom4j.rule.Mode;


import java.io.ByteArrayInputStream;

public class ListaSegnalazioni extends VerticalLayout{

    public ListaSegnalazioni(Utente utente,GestioneModerazioneController controller){
        VerticalLayout vertical = new VerticalLayout();
        if(utente.getRuolo() == Ruolo.MODERATORE) {
            Moderatore moderatore = (Moderatore) utente;
            for (Segnalazione s : moderatore.getSegnalazioneRicevute()) {
                if(s.getFoto().isVisible() && !(s.getFoto().getProfilo().getStudente().isBanned())) //da ricontrollare
                vertical.addComponentAsFirst(segnalazione(utente, s, controller));
            }
        }else if (utente.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            CommunityManager manager = (CommunityManager) utente;
            for (Segnalazione s : manager.getSegnalazioneRicevute()){
                if(s.getFoto().isVisible() && !(s.getFoto().getProfilo().getStudente().isBanned())) //da ricontrollare
                vertical.addComponentAsFirst(segnalazione(utente,s,controller));
            }
        }
        add(vertical);
    }

    public HorizontalLayout segnalazione(Utente utente,Segnalazione segnalazione, GestioneModerazioneController controller){
        Moderatore moderatore = (Moderatore) utente;

        HorizontalLayout horizontal = new HorizontalLayout();
        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","100px");
        image.getStyle().set("height","100px");
        Span testo = new Span("Hai ricevuto una segnalazione per una foto di : " + segnalazione.getFoto().getProfilo().getNome() + segnalazione.getFoto().getProfilo().getCognome());
        Button dettagliSegnalazione = new Button("Mostra dettagli");
        dettagliSegnalazione.addClickListener(buttonClickEvent -> {
            Notification notificaDettagli = new Notification();
            VerticalLayout verticalNotifica = new VerticalLayout();
            verticalNotifica.setAlignItems(Alignment.CENTER);
            TextField motivazione = new TextField();
            motivazione.setValue(segnalazione.getMotivazione());
            motivazione.setEnabled(false);

            TextField dettagli = new TextField();
            dettagli.setValue(segnalazione.getDettagli());
            dettagli.setEnabled(false);

            Button chiudi = new Button("Chiudi");
            chiudi.addClickListener(buttonClickEvent1 -> {
                notificaDettagli.close();
            });

            verticalNotifica.add(motivazione, dettagli, chiudi);
            notificaDettagli.add(verticalNotifica);
            notificaDettagli.setPosition(Notification.Position.MIDDLE);
            notificaDettagli.open();
        });

        VerticalLayout verticalSegnalazione = new VerticalLayout();
        verticalSegnalazione.add(testo, dettagliSegnalazione);
        horizontal.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontal.add(image,verticalSegnalazione, pulsanteAmmonimento(segnalazione,controller, moderatore));
        if(utente.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            horizontal.add(pulsanteSospensione(segnalazione,controller,moderatore));
        }
        return horizontal;
    }

    public Notification notification;

    public Button pulsanteAmmonimento(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        Button button = new Button("Ammonimento");
        Button annulla = new Button("Annulla");

        notification = new Notification();
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoAmmonimento(segnalazione,controller,moderatore));
        vertical.add(image,horizontal,annulla);

        notification.add(vertical);
        notification.setPosition(Notification.Position.MIDDLE);

        annulla.addClickListener(e -> {
            notification.close();
        });

        button.addClickListener(e -> {
            notification.open();
        });

        return button;
    }

    public Notification sospensione;

    public Button pulsanteSospensione(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        Button button = new Button("Sospensione");
        Button annulla = new Button("Annulla");

        sospensione = new Notification();
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoSospensione(segnalazione,controller,moderatore));
        vertical.add(image,horizontal,annulla);

        sospensione.add(vertical);
        sospensione.setPosition(Notification.Position.MIDDLE);

        annulla.addClickListener(e -> {
            sospensione.close();
        });

        button.addClickListener(e -> {
            sospensione.open();
        });

        return button;
    }

    private HorizontalLayout infoSospensione(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //layout sinistra
        VerticalLayout vertical = new VerticalLayout();
        Span nome = new Span(segnalazione.getFoto().getProfilo().getNome());

        TextField durata = new TextField("(Inserire un numero)");
        durata.setPlaceholder("Durata sospensione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");


        //layout destra
        VerticalLayout vertical_due = new VerticalLayout();

        Span email = new Span(segnalazione.getFoto().getProfilo().getStudente().getEmail());

        com.vaadin.flow.component.button.Button inviaSospensione = new com.vaadin.flow.component.button.Button("Invia Sospensione");
        inviaSospensione.addClickListener(e -> {
            controller.inviaSospensione(Integer.parseInt(durata.getValue()),dettagli.getValue(), segnalazione.getFoto().getProfilo().getStudente());
            notification.close();
            UI.getCurrent().getPage().reload();
        });
        vertical_due.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical_due.add(email, inviaSospensione);

        vertical.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical.add(nome, durata, dettagli);
        horizontalLayout.add(vertical,vertical_due);
        return horizontalLayout;
    }


    public HorizontalLayout infoAmmonimento(Segnalazione segnalazione,GestioneModerazioneController controller, Moderatore moderatore){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //layout sinistra
        VerticalLayout vertical = new VerticalLayout();
        Span nome = new Span(segnalazione.getFoto().getProfilo().getNome());

        TextField motivazione = new TextField();
        motivazione.setPlaceholder("Motivazione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");


        //layout destra
        VerticalLayout vertical_due = new VerticalLayout();

        Span email = new Span(segnalazione.getFoto().getProfilo().getStudente().getEmail());

        com.vaadin.flow.component.button.Button inviaAmmonimento = new com.vaadin.flow.component.button.Button("Invia Ammonimento");
        inviaAmmonimento.addClickListener(e -> {
            controller.inviaAmmonimento(dettagli.getValue(),motivazione.getValue(), moderatore, segnalazione.getFoto().getProfilo().getStudente(), segnalazione.getFoto());
            notification.close();
            UI.getCurrent().getPage().reload();
        });
        vertical_due.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical_due.add(email, inviaAmmonimento);

        vertical.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical.add(nome, motivazione, dettagli);
        horizontalLayout.add(vertical,vertical_due);
        return horizontalLayout;
    }
}
