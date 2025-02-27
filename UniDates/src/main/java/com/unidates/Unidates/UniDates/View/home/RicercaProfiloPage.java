package com.unidates.Unidates.UniDates.View.home;

import com.unidates.Unidates.UniDates.Control.InteractionControl;
import com.unidates.Unidates.UniDates.Control.UserManagementControl;
import com.unidates.Unidates.UniDates.DTOs.FotoDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Enum.Hobby;
import com.unidates.Unidates.UniDates.Model.Enum.Interessi;
import com.unidates.Unidates.UniDates.View.navbar.Navbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.html.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.util.Locale;

@Route(value = "ricercaprofilo",layout = Navbar.class)
public class RicercaProfiloPage extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    InteractionControl interactionControl;
    @Autowired
    UserManagementControl userManagementControl;

    VerticalLayout tot;
    StudenteDTO daCercare;
    StudenteDTO inSessione;

    H3 nome;
    H3 cognome;
    Span topics = new Span();
    Span residenza = new Span();
    Span nascita = new Span();
    Span colore_occhi = new Span();
    Span colore_capelli = new Span();
    Span altezza = new Span();
    Span compleanno = new Span();
    Span numero = new Span();
    Span instagram = new Span();
    Image interessiImage = new Image();

    public RicercaProfiloPage(){
    }

    public VerticalLayout Page() {
        VerticalLayout allPage = new VerticalLayout();

        //padre
        HorizontalLayout horizontal = new HorizontalLayout();

        //layout immagine
        VerticalLayout image_layout = new VerticalLayout();
        StreamResource resource = new StreamResource("ciao", () -> new ByteArrayInputStream(daCercare.getProfilo().getListaFoto().get(0).getImg()));
        Image image_profilo = new Image(resource, "");
        image_profilo.getStyle().set("width","250px");
        image_profilo.getStyle().set("height","250px");
        Button bloccaSblocca;
        if(inSessione.getListaBloccatiEmail().contains(daCercare.getEmail())){
            bloccaSblocca = new Button("Sblocca");
            bloccaSblocca.setId("blocca-sblocca");
            bloccaSblocca.addClickListener(buttonClickEvent -> {
                interactionControl.sbloccaStudente(inSessione.getEmail(), daCercare.getEmail());
                UI.getCurrent().getPage().reload();
            });
        }else {
            bloccaSblocca = new Button("Blocca");
            bloccaSblocca.setId("blocca-sblocca");
            bloccaSblocca.addClickListener(buttonClickEvent -> {
                interactionControl.bloccaStudente(inSessione.getEmail(), daCercare.getEmail());
                UI.getCurrent().getPage().reload();
            });
        }
        image_layout.add(image_profilo,bloccaSblocca);




        HorizontalLayout nome_cognome = new HorizontalLayout();
        nome = new H3(daCercare.getProfilo().getNome());
        cognome = new H3(daCercare.getProfilo().getCognome());
        nome_cognome.add(nome, cognome);

        String iter = "";
        for(Hobby  h : daCercare.getProfilo().getHobbyList())
            iter += h.toString().toLowerCase()+ ", " ;

        topics = new Span("Topics: " + iter.toLowerCase());


        if (daCercare.getProfilo().getInteressi().equals(Interessi.UOMINI))
            interessiImage.setSrc("./images/icons/male.png");
        else if (daCercare.getProfilo().getInteressi().equals(Interessi.DONNE))
            interessiImage.setSrc("./images/icons/female.png");
        else if (daCercare.getProfilo().getInteressi().equals(Interessi.ENTRAMBI))
            interessiImage.setSrc("./images/icons/bisex.png");

        interessiImage.setWidth("50px");
        interessiImage.setHeight("50px");
        Span didascalia = new Span("Interessi:");
        HorizontalLayout didascaliaInteressi = new HorizontalLayout();
        didascaliaInteressi.add(didascalia,interessiImage);


        HorizontalLayout città = new HorizontalLayout();
        residenza = new Span("Residenza: " + daCercare.getProfilo().getResidenza());
        nascita = new Span("Città di nascita: " + daCercare.getProfilo().getLuogoNascita());
        città.add(residenza, nascita);

        HorizontalLayout caratteristiche = new HorizontalLayout();
        colore_occhi = new Span("Colore occhi: " + daCercare.getProfilo().getColore_occhi());
        colore_capelli = new Span("Colore capelli: " + daCercare.getProfilo().getColori_capelli());
        altezza = new Span("Altezza: " + daCercare.getProfilo().getAltezza());
        compleanno = new Span("Data di nascita: " + daCercare.getProfilo().getDataDiNascita());
        caratteristiche.add(colore_capelli, colore_occhi, altezza, compleanno);

        HorizontalLayout contatti = new HorizontalLayout();
        if(daCercare.getProfilo().getNumeroTelefono() != null){
            numero = new Span("Numero di cellulare: " +  daCercare.getProfilo().getNumeroTelefono());
            contatti.add(numero);
        }
        if(daCercare.getProfilo().getNickInstagram() != null){
            instagram = new Span("Contatto instagram: " + daCercare.getProfilo().getNickInstagram());
            contatti.add(instagram);
        }




        HorizontalLayout listaFoto = new HorizontalLayout();

        for(FotoDTO f : daCercare.getProfilo().getListaFoto()) {
            StreamResource resource2 = new StreamResource("fotoprofilo", () -> new ByteArrayInputStream(f.getImg()));
            Image img = new Image(resource2, "");
            img.getStyle().set("width", "200px");
            img.getStyle().set("height", "200px");
            listaFoto.add(img);
        }


        if(interactionControl.isValidMatch(inSessione.getEmail(), daCercare.getEmail())){
            VerticalLayout info_layout = new VerticalLayout();
            info_layout.add(nome_cognome, topics, didascaliaInteressi, città,contatti, caratteristiche);
            horizontal.add(image_layout, info_layout);
            allPage.add(horizontal,listaFoto);
        }else {
            VerticalLayout noMatch = new VerticalLayout();
            noMatch.add(nome_cognome,topics, didascaliaInteressi);
            horizontal.add(image_layout,noMatch);
            allPage.add(horizontal);
        }
        return allPage;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        try{
            daCercare = interactionControl.ricercaStudente(s);
        }
        catch(EntityNotFoundException e){
            Notification erroreRicerca = new Notification("Utente non trovato!",5000, Notification.Position.MIDDLE);
            erroreRicerca.open();
        }
        inSessione = userManagementControl.studenteInSessione();


        if (tot != null)
            remove(tot);
        tot = Page();
        tot.setAlignItems(Alignment.CENTER);
        add(tot);
    }

}
