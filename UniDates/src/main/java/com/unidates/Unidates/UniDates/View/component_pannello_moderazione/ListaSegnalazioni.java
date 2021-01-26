package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.AmmonimentoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.SospensioneDTO;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.CommunityManagerDTO;
import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.DTOs.UtenteDTO;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
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


import java.io.ByteArrayInputStream;

public class ListaSegnalazioni extends VerticalLayout{

    GestioneModerazioneController controller;

    GestioneUtentiController gestioneUtentiController;

    GestioneProfiloController gestioneProfiloController;

    ModeratoreDTO moderatore;

    public ListaSegnalazioni(ModeratoreDTO moderatore, GestioneModerazioneController controller, GestioneUtentiController gestioneUtentiController, GestioneProfiloController gestioneProfiloController){
        this.controller = controller;
        this.gestioneUtentiController = gestioneUtentiController;
        this.gestioneProfiloController = gestioneProfiloController;
        VerticalLayout vertical = new VerticalLayout();
        if(moderatore.getRuolo() == Ruolo.MODERATORE) {
            this.moderatore =  moderatore;
            for (SegnalazioneDTO segnalazioneDTO : this.moderatore.getSegnalazioneRicevute()) {
                FotoDTO fotoSegnalata = gestioneProfiloController.trovaFoto(segnalazioneDTO.getFotoId());
                ProfiloDTO profiloSegnalato = gestioneProfiloController.trovaProfilo(fotoSegnalata.getProfiloId());
                StudenteDTO studenteSegnalato =gestioneUtentiController.trovaStudente(profiloSegnalato.getEmailStudente());
                if(fotoSegnalata.isVisible() && !(studenteSegnalato.isBanned())) //da ricontrollare
                vertical.addComponentAsFirst(segnalazione(fotoSegnalata, profiloSegnalato, segnalazioneDTO, studenteSegnalato));
            }
        }else if (moderatore.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            this.moderatore =  moderatore;
            for (SegnalazioneDTO segnalazioneDTO : (moderatore).getSegnalazioneRicevute()){
                SegnalazioneDTO segnalazione = segnalazioneDTO;
                FotoDTO fotoSegnalata = gestioneProfiloController.trovaFoto(segnalazioneDTO.getFotoId());
                ProfiloDTO profiloSegnalato = gestioneProfiloController.trovaProfilo(fotoSegnalata.getProfiloId());
                StudenteDTO studenteSegnalato = gestioneUtentiController.trovaStudente(profiloSegnalato.getEmailStudente());
                if(fotoSegnalata.isVisible() && !(studenteSegnalato.isBanned())) //da ricontrollare
                vertical.addComponentAsFirst(segnalazione(fotoSegnalata, profiloSegnalato, segnalazione, studenteSegnalato));
            }
        }
        add(vertical);
    }

    public HorizontalLayout segnalazione(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteSegnalato){


        HorizontalLayout horizontal = new HorizontalLayout();

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream( fotoSegnalata.getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","100px");
        image.getStyle().set("height","100px");
        Span testo = new Span("Hai ricevuto una segnalazione per una foto di : " + profiloSegnalato.getNome() + profiloSegnalato.getCognome());
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
        horizontal.setAlignItems(Alignment.CENTER);
        horizontal.add(image,verticalSegnalazione, pulsanteAmmonimento(fotoSegnalata, profiloSegnalato, segnalazione,studenteSegnalato));
        if(moderatore.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            horizontal.add(pulsanteSospensione(fotoSegnalata, profiloSegnalato, studenteSegnalato));
        }
        return horizontal;
    }

    public Notification notification;

    public Button pulsanteAmmonimento(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteDTO){
        Button button = new Button("Ammonimento");
        Button annulla = new Button("Annulla");

        notification = new Notification();
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(fotoSegnalata.getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoAmmonimento(fotoSegnalata, profiloSegnalato, studenteDTO));
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
    public Button pulsanteSospensione(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato,StudenteDTO studenteSegnalato){
        Button button = new Button("Sospensione");
        Button annulla = new Button("Annulla");

        sospensione = new Notification();
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(fotoSegnalata.getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoSospensione(profiloSegnalato, studenteSegnalato));
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

    private HorizontalLayout infoSospensione(ProfiloDTO profiloSegnalato, StudenteDTO studenteSegnalato) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //layout sinistra
        VerticalLayout vertical = new VerticalLayout();
        Span nome = new Span(profiloSegnalato.getNome());

        TextField durata = new TextField("(Inserire un numero)");
        durata.setPlaceholder("Durata sospensione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");


        //layout destra
        VerticalLayout vertical_due = new VerticalLayout();

        Span email = new Span(studenteSegnalato.getEmail());

        com.vaadin.flow.component.button.Button inviaSospensione = new com.vaadin.flow.component.button.Button("Invia Sospensione");
        inviaSospensione.addClickListener(e -> {
            SospensioneDTO sospensioneDTO = new SospensioneDTO(Integer.parseInt(durata.getValue()),dettagli.getValue());
            controller.inviaSospensione(sospensioneDTO,studenteSegnalato.getEmail());
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


    public HorizontalLayout infoAmmonimento(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, StudenteDTO studenteSegnalato){
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //layout sinistra
        VerticalLayout vertical = new VerticalLayout();
        Span nome = new Span(profiloSegnalato.getNome());

        TextField motivazione = new TextField();
        motivazione.setPlaceholder("Motivazione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");


        //layout destra
        VerticalLayout vertical_due = new VerticalLayout();

        Span email = new Span(studenteSegnalato.getEmail());

        com.vaadin.flow.component.button.Button inviaAmmonimento = new com.vaadin.flow.component.button.Button("Invia Ammonimento");
        inviaAmmonimento.addClickListener(e -> {
            AmmonimentoDTO ammonimentoDTO = new AmmonimentoDTO(dettagli.getValue(),motivazione.getValue());
            controller.inviaAmmonimento(ammonimentoDTO, moderatore.getEmail(),studenteSegnalato.getEmail(),fotoSegnalata);
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
