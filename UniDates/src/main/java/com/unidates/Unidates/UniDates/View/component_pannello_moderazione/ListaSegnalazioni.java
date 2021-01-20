package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
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


    //prova commit
    public ListaSegnalazioni(Moderatore moderatore,GestioneModerazioneController controller){
        VerticalLayout vertical = new VerticalLayout();
        for(Segnalazione s : moderatore.getSegnalazioneRicevute()){
            vertical.add(segnalazione(s,controller,moderatore));
        }
        add(vertical);
    }

    public HorizontalLayout segnalazione(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        HorizontalLayout horizontal = new HorizontalLayout();
        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","100px");
        image.getStyle().set("height","100px");
        Span testo = new Span("Hai ricevuto una segnalazione per una foto di : " + segnalazione.getFoto().getProfilo().getNome() + segnalazione.getFoto().getProfilo().getCognome());
        horizontal.setAlignItems(FlexComponent.Alignment.CENTER);
        horizontal.add(image, testo, pulsanteSegnalazione(segnalazione,controller, moderatore));
        return horizontal;
    }

    public Notification notification;

    public Button pulsanteSegnalazione(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        Button button = new Button("Apri");
        Button annulla = new Button("Annulla");

        notification = new Notification();
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoSegnalazione(segnalazione,controller,moderatore));
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

    public HorizontalLayout infoSegnalazione(Segnalazione segnalazione,GestioneModerazioneController controller, Moderatore moderatore){
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
            Ammonimento ammonimento = new Ammonimento();
            ammonimento.setDettagli(dettagli.getValue());
            ammonimento.setMotivazione(motivazione.getValue());
            controller.inviaAmmonimento(ammonimento, moderatore, segnalazione.getFoto().getProfilo().getStudente(), segnalazione.getFoto());
            notification.close();
        });
        vertical_due.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical_due.add(email, inviaAmmonimento);

        vertical.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical.add(nome, motivazione, dettagli);
        horizontalLayout.add(vertical,vertical_due);
        return horizontalLayout;
    }
}
