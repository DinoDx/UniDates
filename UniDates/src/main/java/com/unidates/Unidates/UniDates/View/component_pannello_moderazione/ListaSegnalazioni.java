package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.AmmonimentoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.SospensioneDTO;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
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
import org.springframework.beans.factory.annotation.Autowired;


import java.io.ByteArrayInputStream;

public class ListaSegnalazioni extends VerticalLayout{


    GestioneModerazioneController gestioneModerazioneController;


    GestioneUtentiController gestioneUtentiController;


    GestioneProfiloController gestioneProfiloController;

    ModeratoreDTO moderatore;

    public ListaSegnalazioni(ModeratoreDTO moderatore, GestioneUtentiController gestioneUtentiController, GestioneModerazioneController gestioneModerazioneController, GestioneProfiloController gestioneProfiloController){
        this.gestioneModerazioneController = gestioneModerazioneController;
        this.gestioneUtentiController = gestioneUtentiController;
        this.gestioneProfiloController = gestioneProfiloController;
        this.moderatore = moderatore;
        addAttachListener(event -> createListaSegnalazioni());
    }
    public void createListaSegnalazioni(){
        VerticalLayout layoutListaSegnalazioni = new VerticalLayout();
            for (SegnalazioneDTO segnalazioneDTO : moderatore.getSegnalazioneRicevute()) {
                FotoDTO fotoSegnalata = gestioneProfiloController.trovaFoto(segnalazioneDTO.getFotoId());
                ProfiloDTO profiloSegnalato = gestioneProfiloController.trovaProfilo(fotoSegnalata.getProfiloId());
                StudenteDTO studenteSegnalato =gestioneUtentiController.trovaStudente(profiloSegnalato.getEmailStudente());
                if(fotoSegnalata.isVisible() && !(studenteSegnalato.isBanned())) //da ricontrollare
                    layoutListaSegnalazioni.addComponentAsFirst(cardSegnalazione(fotoSegnalata, profiloSegnalato, segnalazioneDTO, studenteSegnalato));
        }
        add(layoutListaSegnalazioni);
    }

    public HorizontalLayout cardSegnalazione(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteSegnalato){


        HorizontalLayout layoutCard = new HorizontalLayout();
        
        Image immagineCard = new Image(new StreamResource("stream",()-> new ByteArrayInputStream( fotoSegnalata.getImg())),"Immagine non trovata!");
        immagineCard.getStyle().set("width","100px");
        immagineCard.getStyle().set("height","100px");
        
        Span testoInfoSegnalazione = new Span("Hai ricevuto una segnalazione per una foto di : " + profiloSegnalato.getNome() + profiloSegnalato.getCognome());
        
        //Notifica di dettagli della segnalazione
        Button mostraDettagliSegnalazione = new Button("Mostra dettagli");
        mostraDettagliSegnalazione.addClickListener(buttonClickEvent -> {
            Notification dettagliSegnalazione = new Notification();
            
            VerticalLayout layoutDettagliSegnalazione = new VerticalLayout();
            layoutDettagliSegnalazione.setAlignItems(Alignment.CENTER);
            
            TextField motivazione = new TextField();
            motivazione.setValue(segnalazione.getMotivazione());
            motivazione.setEnabled(false);

            TextField dettagli = new TextField();
            dettagli.setValue(segnalazione.getDettagli());
            dettagli.setEnabled(false);

            Button chiudiDettagliSegnalazione = new Button("Chiudi");
            chiudiDettagliSegnalazione.addClickListener(buttonClickEvent1 -> {
                dettagliSegnalazione.close();
            });

            layoutDettagliSegnalazione.add(motivazione, dettagli, chiudiDettagliSegnalazione);
            dettagliSegnalazione.add(layoutDettagliSegnalazione);
            dettagliSegnalazione.setPosition(Notification.Position.MIDDLE);
            dettagliSegnalazione.open();
        });

        
        VerticalLayout InfoEMostraDettagliLayout = new VerticalLayout();
        InfoEMostraDettagliLayout.add(testoInfoSegnalazione, mostraDettagliSegnalazione);

        Button apriCardAmmonimento = new Button("Ammonimento");
        apriCardAmmonimento.addClickListener(e->
            notificaAmmonimento(fotoSegnalata, profiloSegnalato, segnalazione,studenteSegnalato).open()
        );

        layoutCard.setAlignItems(Alignment.CENTER);
        layoutCard.add(immagineCard,InfoEMostraDettagliLayout,apriCardAmmonimento);

        if(moderatore.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            Button apriCardSospensione = new Button("Sospensione");
            apriCardSospensione.addClickListener(event -> notificaSospensione(fotoSegnalata, profiloSegnalato, studenteSegnalato).open());
            layoutCard.add(apriCardSospensione);
        }
        return layoutCard;
    }


    public Notification notificaAmmonimento(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteDTO){

        Notification cardAmmonimento = new Notification();

        VerticalLayout layoutCardAmmonimento = new VerticalLayout();
        layoutCardAmmonimento.setAlignItems(FlexComponent.Alignment.CENTER);

        Button chiudiCardAmmonimento = new Button("Annulla");
        chiudiCardAmmonimento.addClickListener(e -> {
            cardAmmonimento.close();
        });


        Image image = new Image(new StreamResource("ciao",()-> new ByteArrayInputStream(fotoSegnalata.getImg())),"Immagine non trovata");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout layoutInternoAmmonimento = new HorizontalLayout();
        layoutInternoAmmonimento.add(infoAmmonimento(cardAmmonimento,fotoSegnalata, profiloSegnalato, studenteDTO));
        layoutCardAmmonimento.add(image,layoutInternoAmmonimento,chiudiCardAmmonimento);

        cardAmmonimento.add(layoutCardAmmonimento);
        cardAmmonimento.setPosition(Notification.Position.MIDDLE);


        return cardAmmonimento;
    }


    public Notification notificaSospensione(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, StudenteDTO studenteSegnalato){
        Notification cardSospensione = new Notification();

        Button annulla = new Button("Annulla");
        annulla.addClickListener(e ->
            cardSospensione.close()
        );

        VerticalLayout layoutCardSospensione = new VerticalLayout();
        layoutCardSospensione.setAlignItems(FlexComponent.Alignment.CENTER);

        Image image = new Image(new StreamResource("ciao",()-> new ByteArrayInputStream(fotoSegnalata.getImg())),"Immagine non trovata");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoSospensione(cardSospensione,profiloSegnalato, studenteSegnalato));
        layoutCardSospensione.add(image,horizontal,annulla);

        cardSospensione.add(layoutCardSospensione);
        cardSospensione.setPosition(Notification.Position.MIDDLE);

        return cardSospensione;
    }

    private HorizontalLayout infoSospensione(Notification cardSospensione, ProfiloDTO profiloSegnalato, StudenteDTO studenteSegnalato) {
        HorizontalLayout layoutInfoSospensione = new HorizontalLayout();

        //layout sinistra
        VerticalLayout layoutSinistraSospensione = new VerticalLayout();

        TextField durata = new TextField("(Inserire un numero)");
        durata.setPlaceholder("Durata sospensione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");

        layoutSinistraSospensione.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutSinistraSospensione.add(new Span(profiloSegnalato.getNome()), durata, dettagli);

        //layout destra
        VerticalLayout layoutDestraSospensione = new VerticalLayout();

        Button inviaSospensione = new Button("Invia Sospensione");
        inviaSospensione.addClickListener(e -> {
            SospensioneDTO sospensioneDTO = new SospensioneDTO(Integer.parseInt(durata.getValue()),dettagli.getValue());
            gestioneModerazioneController.inviaSospensione(sospensioneDTO,studenteSegnalato.getEmail());
            cardSospensione.close();
            UI.getCurrent().getPage().reload();
        });


        layoutDestraSospensione.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutDestraSospensione.add(new Span(studenteSegnalato.getEmail()), inviaSospensione);


        layoutInfoSospensione.add(layoutSinistraSospensione,layoutDestraSospensione);
        return layoutInfoSospensione;
    }


    public HorizontalLayout infoAmmonimento(Notification cardAmmonimento, FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, StudenteDTO studenteSegnalato){

        HorizontalLayout layoutInfoAmmonimento = new HorizontalLayout();

        //layout sinistra
        VerticalLayout layoutSinistraInfo = new VerticalLayout();
        layoutSinistraInfo.setAlignItems(FlexComponent.Alignment.CENTER);

        TextField motivazione = new TextField();
        motivazione.setPlaceholder("Motivazione");

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");

        layoutSinistraInfo.add(new Span(profiloSegnalato.getNome()),
                motivazione, dettagli);


        //layout destra
        VerticalLayout layoutDestraInfo = new VerticalLayout();

        Button inviaAmmonimento = new Button("Invia Ammonimento");
        inviaAmmonimento.addClickListener(e -> {
            AmmonimentoDTO ammonimentoDTO = new AmmonimentoDTO(dettagli.getValue(),motivazione.getValue());
            gestioneModerazioneController.inviaAmmonimento(ammonimentoDTO, moderatore.getEmail(),studenteSegnalato.getEmail(),fotoSegnalata);
            cardAmmonimento.close();
            UI.getCurrent().getPage().reload();

        });

        layoutDestraInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutDestraInfo.add(new Span(studenteSegnalato.getEmail()), inviaAmmonimento);


        layoutInfoAmmonimento.add(layoutSinistraInfo,layoutDestraInfo);
        return layoutInfoAmmonimento;
    }
}
