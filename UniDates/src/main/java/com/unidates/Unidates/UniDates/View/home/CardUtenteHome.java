package com.unidates.Unidates.UniDates.View.home;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.ModerationControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.InvalidFormatException;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;
import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class CardUtenteHome extends Div {

    InteractionControl interactionControl;
    ModerationControl moderationControl;
    UserManagementControl userManagementControl;

    public CardUtenteHome(UserManagementControl userManagementControl, InteractionControl interactionControl, StudenteDTO studenteDTO, ModerationControl moderationControl){
        this.interactionControl = interactionControl;
        this.userManagementControl = userManagementControl;
        this.moderationControl = moderationControl;
        VerticalLayout tot = Card(studenteDTO);
        add(tot);
    }




    public VerticalLayout Card(StudenteDTO studente){


        Image image_profilo = new Image();
        FotoDTO fotoCard = new FotoDTO();
        boolean trovato = false;
        //layput padre
        VerticalLayout contenitore = new VerticalLayout();
        contenitore.setSpacing(false);
        contenitore.getStyle().set("background-color",  "var(--lumo-tint-30pct)");
        contenitore.getStyle().set("border", "1px solid var(--lumo-tint-80pct) ");
        contenitore.getStyle().set("border-radius", "5px");
        contenitore.getStyle().set("width","640px");
        contenitore.getStyle().set("height","725px");

        //layout sinistra con foto e pulsanti
        VerticalLayout layout_foto = new VerticalLayout();
        HorizontalLayout pulsanti = new HorizontalLayout();

        if(studente.getProfilo().getFotoProfilo().isVisible()) {
            trovato = true;
            fotoCard = studente.getProfilo().getFotoProfilo();
            StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(studente.getProfilo().getFotoProfilo().getImg()));
            image_profilo = new Image(resource, "");
            image_profilo.getStyle().set("width", "350px");
            image_profilo.getStyle().set("height", "350px");
        }
        else {

            for(FotoDTO f : studente.getProfilo().getListaFoto()) {
                if(f.isVisible() && !trovato) {
                    fotoCard = f;
                    StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(f.getImg()));
                    image_profilo = new Image(resource, "");
                    image_profilo.getStyle().set("width", "350px");
                    image_profilo.getStyle().set("height", "350px");
                    trovato = true;
                }
            }
        }
        if(trovato) {
            Button like = getLikeButton(studente);
            like.setId("like-button");
            Button report = reportButton(studente, fotoCard);
            report.setId("report-button");
            pulsanti.add(like, report);
            layout_foto.add(image_profilo, pulsanti);
            layout_foto.setAlignItems(FlexComponent.Alignment.CENTER);

            //layout destra con nome interessi e topics
            VerticalLayout layout_info = new VerticalLayout();
            layout_info.setSpacing(false);
            HorizontalLayout nome_cognome = new HorizontalLayout();
            nome_cognome.setSpacing(false);

            Anchor profilo = new Anchor("ricercaprofilo/" + studente.getEmail());
            Span nome = new Span(studente.getProfilo().getNome() + " " + studente.getProfilo().getCognome());
            profilo.add(nome);
            nome_cognome.add(profilo);


            VerticalLayout layoutTotalTopic = new VerticalLayout();
            layoutTotalTopic.setSpacing(false);
            HorizontalLayout layoutTopicUno = new HorizontalLayout();
            HorizontalLayout layoutTopicDue = new HorizontalLayout();
            HorizontalLayout layoutTopicTre = new HorizontalLayout();
            HorizontalLayout layoutTopicQuattro = new HorizontalLayout();
            HorizontalLayout layoutTopicCinque = new HorizontalLayout();

            HorizontalLayout titleTopic = new HorizontalLayout(new Span("Topic:"));
            titleTopic.setSpacing(false);

            studente.getProfilo().getHobbyList().forEach( topic -> {
                if(layoutTopicUno.getComponentCount() < 5){
                    Button topicSingolo = new Button(topic.toString().toLowerCase());
                    topicSingolo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    topicSingolo.setEnabled(false);
                    layoutTopicUno.add(topicSingolo);
                }else if(layoutTopicDue.getComponentCount() <5){
                    Button topicSingolo = new Button(topic.toString().toLowerCase());
                    topicSingolo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    topicSingolo.setEnabled(false);
                    layoutTopicDue.add(topicSingolo);
                }else if(layoutTopicTre.getComponentCount() <5) {
                    Button topicSingolo = new Button(topic.toString().toLowerCase());
                    topicSingolo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    topicSingolo.setEnabled(false);
                    layoutTopicTre.add(topicSingolo);
                }else if(layoutTopicQuattro.getComponentCount() <5) {
                    Button topicSingolo = new Button(topic.toString().toLowerCase());
                    topicSingolo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    topicSingolo.setEnabled(false);
                    layoutTopicQuattro.add(topicSingolo);
                }else if(layoutTopicCinque.getComponentCount() <5) {
                    Button topicSingolo = new Button(topic.toString().toLowerCase());
                    topicSingolo.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                    topicSingolo.setEnabled(false);
                    layoutTopicCinque.add(topicSingolo);
                }
            });;

            Span interessi = new Span("Interessato a: " + studente.getProfilo().getInteressi().toString().toLowerCase());
            VerticalLayout interessiLayout = new VerticalLayout();
            interessiLayout.setSpacing(false);
            interessiLayout.add(interessi);

            layoutTotalTopic.add(titleTopic,layoutTopicUno,layoutTopicDue,layoutTopicTre,layoutTopicQuattro,layoutTopicCinque);
            layoutTotalTopic.setSpacing(false);

            layout_info.add(interessiLayout,layoutTotalTopic);

            contenitore.add(nome_cognome,layout_foto, layout_info);
            contenitore.setAlignItems(FlexComponent.Alignment.CENTER);
        }
        return contenitore;
    }


    private Button getLikeButton(StudenteDTO studente) {
        Button like = new Button(new Icon(VaadinIcon.HEART));
        like.getStyle().set("color","white");
        like.addClickListener((buttonClickEvent)->{
            Style style = buttonClickEvent.getSource().getStyle();
            if(style.get("color").equals("white")) {
                buttonClickEvent.getSource().getStyle().set("color", "red");
                try {
                    interactionControl.aggiungiMatch(userManagementControl.studenteInSessione().getEmail(), studente.getEmail());
                }
                catch (InvalidFormatException invalidFormatException){
                    invalidFormatException.printStackTrace();
                }

            }else {
                style.set("color","white");
            }
        });
        return like;
    }

    private Button reportButton(StudenteDTO studente, FotoDTO fotoDTO){
        //Notifica Segnalazione
        Notification notifica = new Notification();
        notifica.setId("report");
        notifica.setPosition(Notification.Position.MIDDLE);
        VerticalLayout layout_report = new VerticalLayout();

        //Report motivazione
        //Select<String> reporting = new Select<>();
        Motivazione[] motivaziones =  Motivazione.values();
        RadioButtonGroup<String> radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setId("motivi");
        radioButtonGroup.setItems(motivaziones[0].toString(), motivaziones[1].toString(), motivaziones[2].toString(), motivaziones[3].toString(), motivaziones[4].toString());

        //reporting.setItems();


        //Report dettagli
        TextArea dettagli = new TextArea();
        dettagli.setId("dettagli");
        dettagli.setPlaceholder("Dettagli segnalazione");
        Button invio = new Button("Invia report",buttonClickEvent -> {
            if(radioButtonGroup.isEmpty()){
                Notification errore = new Notification("Motivazione non valida",2000, Notification.Position.MIDDLE);
                errore.setId("errore-report");
                errore.open();
            }
            else if(dettagli.isEmpty()){
                Notification errore = new Notification("Dettagli non validi",2000, Notification.Position.MIDDLE);
                errore.setId("errore-report");
                errore.open();
            }
            else {
                SegnalazioneDTO segnalazioneDTO = new SegnalazioneDTO(Motivazione.valueOf(radioButtonGroup.getValue()), dettagli.getValue());
                try {
                    moderationControl.inviaSegnalazione(segnalazioneDTO, fotoDTO.getId()); // se la foto mostrata in home non é la foto del profilo, viene mos
                } catch (InvalidFormatException c) {
                    Notification errore = new Notification(c.getMessage(), 2000, Notification.Position.MIDDLE);
                    errore.setId("errore-report");
                    errore.open();
                }

                notifica.close();
            }
        });
        invio.setId("send-report");
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
