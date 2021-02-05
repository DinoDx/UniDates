package com.unidates.Unidates.UniDates.View.moderazione;

import com.unidates.Unidates.UniDates.Controller.InteractionControl;
import com.unidates.Unidates.UniDates.Controller.ModerationControl;
import com.unidates.Unidates.UniDates.Controller.ModifyProfileControl;
import com.unidates.Unidates.UniDates.Controller.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.AmmonimentoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.SospensioneDTO;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.ModeratoreDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidBanFormatException;
import com.unidates.Unidates.UniDates.Exception.InvalidWarningFormatException;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;


import java.io.ByteArrayInputStream;

public class ListaSegnalazioni extends VerticalLayout{


    ModerationControl moderationControl;


    UserManagementControl userManagementControl;

    ModifyProfileControl modifyProfileControl;

    InteractionControl interactionControl;

    ModeratoreDTO moderatore;

    public ListaSegnalazioni(ModeratoreDTO moderatore, UserManagementControl userManagementControl, ModerationControl moderationControl, ModifyProfileControl modifyProfileControl, InteractionControl interactionControl){
        this.moderationControl = moderationControl;
        this.userManagementControl = userManagementControl;
        this.modifyProfileControl = modifyProfileControl;
        this.interactionControl = interactionControl;
        this.moderatore = moderatore;
        addAttachListener(event -> createListaSegnalazioni());
    }
    public void createListaSegnalazioni(){
        VerticalLayout layoutListaSegnalazioni = new VerticalLayout();
            for (SegnalazioneDTO segnalazioneDTO : moderatore.getSegnalazioneRicevute()) {
                FotoDTO fotoSegnalata = modifyProfileControl.trovaFoto(segnalazioneDTO.getFotoId());
                ProfiloDTO profiloSegnalato = modifyProfileControl.trovaProfilo(fotoSegnalata.getProfiloId());
                StudenteDTO studenteSegnalato = interactionControl.ricercaStudente(profiloSegnalato.getEmailStudente());
                if(fotoSegnalata.isVisible() && !(studenteSegnalato.isBanned())) //da ricontrollare
                    layoutListaSegnalazioni.addComponentAsFirst(cardSegnalazione(fotoSegnalata, profiloSegnalato, segnalazioneDTO, studenteSegnalato));
        }
        add(layoutListaSegnalazioni);
    }

    Notification dettagliSegnalazione = new Notification();
    Notification notificaAmm = new Notification();
    Notification notifica = new Notification();


    public HorizontalLayout cardSegnalazione(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteSegnalato){

        HorizontalLayout layoutCard = new HorizontalLayout();
        Image immagineCard = new Image(new StreamResource("stream",()-> new ByteArrayInputStream( fotoSegnalata.getImg())),"Immagine non trovata!");
        immagineCard.getStyle().set("width","150px");
        immagineCard.getStyle().set("height","150px");
        
        Span testoInfoSegnalazione = new Span("Hai ricevuto una segnalazione per una foto di : " + profiloSegnalato.getNome() + profiloSegnalato.getCognome());
        
        //Notifica di dettagli della segnalazione


        Button mostraDettagliSegnalazione = new Button("Mostra dettagli");
        mostraDettagliSegnalazione.addClickListener(buttonClickEvent -> {

            if(!dettagliSegnalazione.isOpened()) {
                dettagliSegnalazione = createNotificaDettagliSegnalazione(segnalazione, fotoSegnalata);
                dettagliSegnalazione.open();
            }
            else dettagliSegnalazione.close();

        });


        Button sospensioneCm = new Button("Invia a CM");
        sospensioneCm.setWidth("250px");
        sospensioneCm.addClickListener(buttonClickEvent -> {
            moderationControl.inviaSegnalazioneCommunityManager(segnalazione,fotoSegnalata);
        });
        
        VerticalLayout InfoEMostraDettagliLayout = new VerticalLayout();
        InfoEMostraDettagliLayout.add(testoInfoSegnalazione, mostraDettagliSegnalazione);

        Button apriCardAmmonimento = new Button("Ammonimento");
        apriCardAmmonimento.setWidth("250px");
        apriCardAmmonimento.addClickListener(e-> {

            if(!notificaAmm.isOpened()){
                notificaAmm = createNotificaAmmonimento(fotoSegnalata, profiloSegnalato, segnalazione, studenteSegnalato);
                notificaAmm.open();
            }
            else notificaAmm.close();

        });

        layoutCard.setAlignItems(Alignment.CENTER);
        layoutCard.add(immagineCard,InfoEMostraDettagliLayout,apriCardAmmonimento);

        if(moderatore.getRuolo() == Ruolo.COMMUNITY_MANAGER){
            Button apriCardSospensione = new Button("Sospensione");
            apriCardSospensione.setWidth("230px");
            Notification notificaSos = notificaSospensione(fotoSegnalata, profiloSegnalato, studenteSegnalato);
            apriCardSospensione.addClickListener( event ->{
                if(!notificaSos.isOpened())
                           notificaSos.open();
                else notificaSos.close();
            });
            layoutCard.add(apriCardSospensione);
        }
        else{
        layoutCard.add(sospensioneCm);
        }
        return layoutCard;
    }

    private Notification createNotificaDettagliSegnalazione(SegnalazioneDTO segnalazione, FotoDTO fotoSegnalata) {

        Notification dettagliSegnalazione = new Notification();
        Image image = new Image(new StreamResource("ciao", () -> new ByteArrayInputStream(fotoSegnalata.getImg())), "Immagine non trovata");
        image.getStyle().set("width", "250px");
        image.getStyle().set("height", "250px");

        VerticalLayout layoutDettagliSegnalazione = new VerticalLayout();
        layoutDettagliSegnalazione.setAlignItems(Alignment.CENTER);

        TextField motivazione = new TextField();
        motivazione.setValue(segnalazione.getMotivazione().toString());
        motivazione.setEnabled(false);

        TextField dettagli = new TextField();
        dettagli.setValue(segnalazione.getDettagli());
        dettagli.setEnabled(false);

        Button chiudiDettagliSegnalazione = new Button("Chiudi");
        chiudiDettagliSegnalazione.addClickListener(buttonClickEvent1 -> {
            dettagliSegnalazione.close();
        });


        layoutDettagliSegnalazione.add(image, motivazione, dettagli, chiudiDettagliSegnalazione);
        dettagliSegnalazione.add(layoutDettagliSegnalazione);
        dettagliSegnalazione.setPosition(Notification.Position.MIDDLE);

        return dettagliSegnalazione;
    }


    public Notification createNotificaAmmonimento(FotoDTO fotoSegnalata, ProfiloDTO profiloSegnalato, SegnalazioneDTO segnalazione, StudenteDTO studenteDTO){

        Notification cardAmmonimento = new Notification();

        VerticalLayout layoutCardAmmonimento = new VerticalLayout();
        layoutCardAmmonimento.setAlignItems(FlexComponent.Alignment.CENTER);

        Button chiudiCardAmmonimento = new Button("Annulla");
        chiudiCardAmmonimento.addClickListener(e -> {
            cardAmmonimento.close();
        });

        HorizontalLayout layoutInternoAmmonimento = new HorizontalLayout();
        layoutInternoAmmonimento.add(infoAmmonimento(cardAmmonimento,fotoSegnalata, profiloSegnalato, studenteDTO));
        layoutCardAmmonimento.add(layoutInternoAmmonimento,chiudiCardAmmonimento);

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
            try {
                SospensioneDTO sospensioneDTO = new SospensioneDTO(Integer.parseInt(durata.getValue()), dettagli.getValue());
                moderationControl.inviaSospensione(sospensioneDTO, studenteSegnalato.getEmail());
            }catch (InvalidBanFormatException ex){
                new Notification("Dettagli e/o durata non validi",2000, Notification.Position.MIDDLE).open();
            }
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

        RadioButtonGroup<String> motivazione = new RadioButtonGroup<>();
        Motivazione[] motivaziones =  Motivazione.values();
        motivazione.setItems(motivaziones[0].toString(), motivaziones[1].toString(), motivaziones[2].toString(), motivaziones[3].toString(), motivaziones[4].toString());

        VerticalLayout motivazioneVerticalLayou = new VerticalLayout();
        motivazioneVerticalLayou.setAlignItems(Alignment.CENTER);
        motivazioneVerticalLayou.add(motivazione);

        TextField dettagli = new TextField();
        dettagli.setPlaceholder("Dettagli");

        layoutSinistraInfo.add(new Span(profiloSegnalato.getNome()),
                motivazioneVerticalLayou, dettagli);


        //layout destra
        VerticalLayout layoutDestraInfo = new VerticalLayout();

        Button inviaAmmonimento = new Button("Invia Ammonimento");
        inviaAmmonimento.addClickListener(e -> {
            try {
                AmmonimentoDTO ammonimentoDTO = new AmmonimentoDTO(Motivazione.valueOf(motivazione.getValue()), dettagli.getValue());
                moderationControl.inviaAmmonimento(ammonimentoDTO, moderatore.getEmail(), studenteSegnalato.getEmail(), fotoSegnalata);
            }catch(InvalidWarningFormatException ex1){
                new Notification("Motivazione e/o dettagli non validi",2000, Notification.Position.MIDDLE).open();
            }
            cardAmmonimento.close();
            UI.getCurrent().getPage().reload();

        });

        layoutDestraInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        layoutDestraInfo.add(new Span(studenteSegnalato.getEmail()), inviaAmmonimento);


        layoutInfoAmmonimento.add(layoutSinistraInfo,layoutDestraInfo);
        return layoutInfoAmmonimento;
    }
}
