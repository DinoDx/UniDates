package com.unidates.Unidates.UniDates.View.home;

import com.unidates.Unidates.UniDates.Controller.InteractionControl;
import com.unidates.Unidates.UniDates.Controller.ModerationControl;
import com.unidates.Unidates.UniDates.Controller.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;


public class CardUtenteHome extends Div {

    InteractionControl interactionControl;
    ModerationControl moderationControl;
    UserManagementControl userManagementControl;

    public CardUtenteHome(UserManagementControl userManagementControl, InteractionControl interactionControl, StudenteDTO studenteDTO, ModerationControl moderationControl){
        this.interactionControl = interactionControl;
        this.userManagementControl = userManagementControl;
        this.moderationControl = moderationControl;
        HorizontalLayout tot = Card(studenteDTO);
        add(tot);
    }




    public HorizontalLayout Card(StudenteDTO studente){
        Image image_profilo = new Image();

        //layput padre
        HorizontalLayout contenitore = new HorizontalLayout();
        contenitore.setSpacing(false);
        //layout sinistra con foto e pulsanti
        VerticalLayout layout_foto = new VerticalLayout();
        HorizontalLayout pulsanti = new HorizontalLayout();

        if(studente.getProfilo().getFotoProfilo().isVisible()) {
            StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(studente.getProfilo().getFotoProfilo().getImg()));
            image_profilo = new Image(resource, "");
            image_profilo.getStyle().set("width", "250px");
            image_profilo.getStyle().set("height", "250px");
        }else {
            boolean trovato = false;
            for(FotoDTO f : studente.getProfilo().getListaFoto()) {
                if(f.isVisible() && !trovato) {
                    StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(f.getImg()));
                    image_profilo = new Image(resource, "");
                    image_profilo.getStyle().set("width", "250px");
                    image_profilo.getStyle().set("height", "250px");
                    trovato = true;
                }
            }
            if(!trovato){
                image_profilo = new Image("https://www.svaghiamo.it/wp-content/uploads/2016/09/image-not-found-4a963b95bf081c3ea02923dceaeb3f8085e1a654fc54840aac61a57a60903fef.png", "Non è possibile caricare la foto");
                image_profilo.getStyle().set("width", "250px");
                image_profilo.getStyle().set("height", "250px");
            }
        }
        Button like = getLikeButton(studente);
        Button report = reportButton(studente);
        pulsanti.add(like,report);
        layout_foto.add(image_profilo,pulsanti);

        //layout destra con nome interessi e topics
        VerticalLayout layout_info = new VerticalLayout();
        HorizontalLayout nome_cognome = new HorizontalLayout();

        Anchor profilo  = new Anchor("ricercaprofilo/"+studente.getEmail());
        Span nome = new Span(studente.getProfilo().getNome() + " " + studente.getProfilo().getCognome());
        profilo.add(nome);
        nome_cognome.add(profilo);

        Span topics = new Span(studente.getProfilo().getHobbyList().toString());
        Span interessi = new Span("Interessato a:"+studente.getProfilo().getInteressi().toString());
        layout_info.add(nome_cognome,topics,interessi);

        contenitore.add(layout_foto,layout_info);
        return contenitore;
    }


    private Button getLikeButton(StudenteDTO studente) {
        Button like = new Button(new Icon(VaadinIcon.HEART));
        like.getStyle().set("color","white");
        like.addClickListener((buttonClickEvent)->{
            Style style = buttonClickEvent.getSource().getStyle();
            if(style.get("color").equals("white")) {
                buttonClickEvent.getSource().getStyle().set("color", "red");
                interactionControl.aggiungiMatch(userManagementControl.studenteInSessione().getEmail(), studente.getEmail());
            }else {
                style.set("color","white");
            }
        });
        return like;
    }

    private Button reportButton(StudenteDTO studente){
        //Notifica Segnalazione
        Notification notifica = new Notification();
        notifica.setPosition(Notification.Position.MIDDLE);
        VerticalLayout layout_report = new VerticalLayout();

        //Report motivazione
        //Select<String> reporting = new Select<>();
        Motivazione[] motivaziones =  Motivazione.values();
        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(motivaziones[0].toString(), motivaziones[1].toString(), motivaziones[2].toString(), motivaziones[3].toString(), motivaziones[4].toString());

        //reporting.setItems();


        //Report dettagli
        TextArea dettagli = new TextArea();
        dettagli.setPlaceholder("Dettagli segnalazione");
        Button invio = new Button("Invia report",buttonClickEvent -> {
            SegnalazioneDTO segnalazioneDTO = new SegnalazioneDTO(Motivazione.valueOf(radioButtonGroup.getValue()), dettagli.getValue());
            try {
                moderationControl.inviaSegnalazione(segnalazioneDTO,studente.getProfilo().getFotoProfilo());
            }catch (InvalidFormatException c){
                new Notification("Motivazione e/o dettagli non validi.",2000, Notification.Position.MIDDLE).open();
            }

            notifica.close();
        });
        Button annulla = new Button("Annulla",buttonClickEvent -> {
            notifica.close();
        });
        Button blocca = new Button("Blocca");
        blocca.addClickListener(buttonClickEvent -> {
            interactionControl.bloccaStudente(userManagementControl.studenteInSessione().getEmail(),studente.getEmail() );
            notifica.close();
            UI.getCurrent().getPage().reload();
        });


        Span motivoSpan = new Span("Seleziona un motivo");
        Span dettagliSpan = new Span("Inserisci i dettagli");

        HorizontalLayout pulsantiSegnalazione = new HorizontalLayout();
        pulsantiSegnalazione.add(invio,blocca,annulla);

        layout_report.setAlignItems(FlexComponent.Alignment.CENTER);
        layout_report.add(motivoSpan,radioButtonGroup,dettagliSpan,dettagli,pulsantiSegnalazione);
        notifica.add(layout_report);
        //Pulsante Report
        Button report = new Button("Report",new Icon(VaadinIcon.PENCIL),buttonClickEvent->{
            notifica.open();
        });
        report.getStyle().set("color","white");
        return report;
    }
}
