package com.unidates.Unidates.UniDates.View.component_home_page;


import com.example.application.views.Person;
import com.unidates.Unidates.UniDates.Controller.GestioneProfiloController;
import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.Enum.Colore_Occhi;
import com.unidates.Unidates.UniDates.Enum.Colori_Capelli;
import com.unidates.Unidates.UniDates.Enum.Hobby;
import com.unidates.Unidates.UniDates.Enum.Interessi;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewProfile;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.servlet.http.HttpSession;
import java.util.*;


@Route(value = "profilo-personale", layout = MainViewProfile.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout implements AfterNavigationObserver {


    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);
    Studente registrato= (Studente) httpSession.getAttribute("utente");

    @Autowired
    GestioneUtentiController controller;

    Grid<Profilo> grid = new Grid<>();
    private Select<String> interessi = new Select<>();

    public ProfiloPersonale(){
        setId("personal-profile");
        addClassName("personal-profile");
        setSizeFull();
        grid.setWidth("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn((profile -> createSingleUser(profile)));
        add(grid);
    }

    private VerticalLayout createSingleUser(Profilo profile){
        VerticalLayout utente = new VerticalLayout();
        utente.addClassName("card-home");
        utente.setSpacing(false);
        utente.getThemeList().add("spacing-s");

        //First Layout
        HorizontalLayout nome = new HorizontalLayout();
        nome.addClassName("top-layout");
        nome.setSpacing(false);
        nome.getThemeList().add("spacing-s");

        Span nome_utente = new Span(registrato.getProfilo().getNome());
        nome.addClassName("nome");
        nome.add(nome_utente);

        //Second Layout
        HorizontalLayout middle = new HorizontalLayout();
        middle.addClassName("middle-layout");
        middle.setSpacing(false);
        middle.getThemeList().add("spacing-s");

        //IMMAGINE DA VEDERE PER LA SESSIONE
        VerticalLayout image = new VerticalLayout();
        image.addClassName("photo");
        Image image_profilo = new Image();
      /*  ArrayList<Foto> list = (ArrayList<Foto>) registrato.getProfilo().getListaFoto();
        image_profilo.setSrc(list.get(0).getUrl());*/
        image_profilo.setAlt("Foto non presente");
        image.setId("foto");
        image.add(image_profilo);



        VerticalLayout all_info = new VerticalLayout();

        //info 1
        HorizontalLayout info_uno = new HorizontalLayout();
        TextField name = new TextField("Nome");
        name.setValue(registrato.getProfilo().getNome());
        name.setEnabled(false);

        TextField cognome = new TextField("Cognome");
        cognome.setValue(registrato.getProfilo().getCognome());
        cognome.setEnabled(false);

        //DATA DA VEDERE PER LA SESSIONE
        DatePicker date = new DatePicker("Data di nascita");
        date.setValue(registrato.getProfilo().getDataDiNascita());
        date.setEnabled(false);

        EmailField email_profilo = new EmailField("Email");
        email_profilo.setValue(registrato.getEmail());
        email_profilo.setEnabled(false);

        info_uno.add(name,cognome,date,email_profilo);

        //info 2
        HorizontalLayout info_due = new HorizontalLayout();

        NumberField altezza = new NumberField("Altezza (cm)");
        altezza.setValue(registrato.getProfilo().getAltezza());
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);
        altezza.setEnabled(false);

        TextField città = new TextField("Città");
        città.setValue(registrato.getProfilo().getResidenza());
        città.setEnabled(false);
        TextField luogo = new TextField("Luogo di nascita");
        luogo.setValue(registrato.getProfilo().getLuogoNascita());
        luogo.setEnabled(false);

        info_due.add(altezza,città,luogo);

        //info 3
        HorizontalLayout info_tre = new HorizontalLayout();



        TextField capelli = new TextField("Capelli");
        capelli.setValue(registrato.getProfilo().getColori_capelli().toString());
        capelli.setEnabled(false);

        TextField occhi = new TextField("Occhi");
        occhi.setValue(registrato.getProfilo().getColore_occhi().toString());
        occhi.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(registrato.getProfilo().getInteressi().toString());
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        interessi.setEnabled(false);

        MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
        multiselectComboBox.setWidth("100%");
        multiselectComboBox.setLabel("Topic Selezionati..");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);
        //multiselectComboBox.setEnabled(false);

        info_tre.add(capelli,occhi,interessi,multiselectComboBox);


        MemoryBuffer img = new MemoryBuffer();
        Upload upload = new Upload(img);
        upload.setMaxFiles(1);
        upload.getStyle().set("margin-top","4em");


        all_info.add(info_uno,info_due,info_tre,upload);
        middle.add(image,all_info);

        //Thirs layout
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addClassName("bottom-layout");
        buttons.setSpacing(false);
        buttons.getThemeList().add("spacing-s");

        Button conferma = new Button("Conferma", buttonClickEvent -> {
            name.setEnabled(false);
            cognome.setEnabled(false);
            date.setEnabled(false);
            email_profilo.setEnabled(false);
            città.setEnabled(false);
            luogo.setEnabled(false);
            occhi.setEnabled(false);
            capelli.setEnabled(false);
            multiselectComboBox.setEnabled(false);
            altezza.setEnabled(false);
            interessi.setEnabled(false);
            //VEDERE IMMAGINE

            registrato.getProfilo().setAltezza(altezza.getValue());
            registrato.getProfilo().setNome(name.getValue());
            registrato.getProfilo().setCognome(cognome.getValue());
            registrato.getProfilo().setDataDiNascita(date.getValue());
            registrato.setEmail(email_profilo.getValue());
            registrato.getProfilo().setResidenza(città.getValue());
            registrato.getProfilo().setLuogoNascita(luogo.getValue());
            registrato.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
            registrato.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
            registrato.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
            //hobby
            ArrayList<Hobby> hob = new ArrayList<Hobby>();
            for(String s : multiselectComboBox.getValue()) hob.add(Hobby.valueOf(s));
            registrato.getProfilo().setHobbyList(hob);

            controller.updateStudente(registrato,registrato.getProfilo());

        });
        conferma.setId("confirm");

        Button modifica = new Button("Modifica",buttonClickEvent -> {
            name.setEnabled(true);
            cognome.setEnabled(true);
            date.setEnabled(true);
            email_profilo.setEnabled(true);
            città.setEnabled(true);
            luogo.setEnabled(true);
            occhi.setEnabled(true);
            capelli.setEnabled(true);
            multiselectComboBox.setEnabled(true);
            altezza.setEnabled(true);
            interessi.setEnabled(true);
            multiselectComboBox.setEnabled(true);
        });
        modifica.setId("modifica");


        Notification notifica = new Notification();
        notifica.setId("notifica");
        PasswordField propria = new PasswordField("Inserisci password corrente");
        propria.getStyle().set("margin-left","2em");
        PasswordField prima = new PasswordField("Nuova password:");
        prima.getStyle().set("margin-left","2em");
        PasswordField secondo = new PasswordField("Conferma password");
        secondo.getStyle().set("margin-left","2em");
        Button annulla = new Button("Annulla",buttonClickEvent -> {
            notifica.close();
        });
        annulla.getStyle().set("margin-left","1em");
        Button conf = new Button("Conferma",buttonClickEvent -> {
            //implmenmtare invio notifica
        });
        conf.getStyle().set("margin-left","2em");
        notifica.add(propria,prima,secondo,conf,annulla);


        Button chpassword = new Button("Cambia Password",buttonClickEvent -> {
            notifica.open();
        });
        chpassword.setId("pass");


        buttons.add(modifica,conferma,chpassword);


        utente.add(nome,middle,buttons);
        return  utente;

    }


    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        List<Profilo> persons = Arrays.asList( //
                registrato.getProfilo()
        );

        grid.setItems(persons);
    }


}
