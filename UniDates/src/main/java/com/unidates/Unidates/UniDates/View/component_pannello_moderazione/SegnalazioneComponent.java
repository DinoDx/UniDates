package com.unidates.Unidates.UniDates.View.component_pannello_moderazione;

import com.unidates.Unidates.UniDates.Controller.GestioneModerazioneController;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;

public class SegnalazioneComponent extends Div {

    TextField motivazione;
    TextField dettagli;

    public SegnalazioneComponent(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        VerticalLayout vertical = new VerticalLayout();
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);

        StreamResource resource = new StreamResource("ciao",()-> new ByteArrayInputStream(segnalazione.getFoto().getImg()));
        Image image = new Image(resource,"");
        image.getStyle().set("width","250px");
        image.getStyle().set("height","250px");

        HorizontalLayout horizontal = new HorizontalLayout();
        horizontal.add(infoSegnalazione(segnalazione), email(segnalazione, controller, moderatore));

        vertical.add(image,horizontal);
        add(vertical);
    }

    public VerticalLayout infoSegnalazione(Segnalazione segnalazione){
        VerticalLayout vertical = new VerticalLayout();
        Span nome = new Span(segnalazione.getFoto().getProfilo().getNome());

        motivazione = new TextField();
        motivazione.setPlaceholder("Motivazione");

        dettagli= new TextField();
        dettagli.setPlaceholder("Dettagli");

        vertical.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical.add(nome, motivazione, dettagli);
        return vertical;
    }

    public VerticalLayout email(Segnalazione segnalazione, GestioneModerazioneController controller, Moderatore moderatore){
        VerticalLayout vertical = new VerticalLayout();

        Span email = new Span(segnalazione.getFoto().getProfilo().getStudente().getEmail());

         Button inviaAmmonimento = new Button("Invia Ammonimento");
        inviaAmmonimento.addClickListener(e -> {
            controller.inviaAmmonimento(dettagli.getValue(),motivazione.getValue(), moderatore, segnalazione.getFoto().getProfilo().getStudente(), segnalazione.getFoto());
        });
        vertical.setAlignItems(FlexComponent.Alignment.CENTER);
        vertical.add(email, inviaAmmonimento);
        return vertical;

    }
}
