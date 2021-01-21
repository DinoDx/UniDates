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
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Security.SecurityUtils;
import com.unidates.Unidates.UniDates.View.main.MainViewProfile;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
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
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.vaadin.gatanaso.MultiselectComboBox;

import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;


@Route(value = "profilo-personale", layout = MainViewProfile.class)
@PageTitle("Profilo")
@CssImport("./styles/views/home/profilopersonale.css")
public class ProfiloPersonale extends VerticalLayout {
    Utente utente = SecurityUtils.getLoggedIn();
    Studente studente = (Studente) utente;
    Profilo profilo = studente.getProfilo();

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    GestioneUtentiController utente_controller;

    @Autowired
    GestioneProfiloController controller;

    public ProfiloPersonale(GestioneProfiloController controller){
        this.controller = controller;


        multiselectComboBox.setLabel("Seleziona Topic");
        multiselectComboBox.setPlaceholder("Scelti...");
        Hobby [] topic = Hobby.values();
        List<String> topiclist = new ArrayList<String>();
        for(Hobby h : topic) topiclist.add(h.toString());
        multiselectComboBox.setItems(topiclist);
        multiselectComboBox.getStyle().set("margin-bottom","30px");


        VerticalLayout padre = new VerticalLayout();
        padre.setAlignItems(Alignment.CENTER);

        HorizontalLayout sotto_padre = new HorizontalLayout();
        VerticalLayout totale_info = new VerticalLayout();
        totale_info.add(Info1(),Info2(),Info3(),Info4());


        sotto_padre.add(ImageUtente(),totale_info);
        padre.add(NomeUtente(),sotto_padre,multiselectComboBox,Pulsanti());
        add(padre);
    }

    public HorizontalLayout NomeUtente(){
        HorizontalLayout nome = new HorizontalLayout();
        Span nome_utente = new Span(profilo.getNome());
        Span cognome_utente = new Span(profilo.getCognome());
        nome.add(nome_utente,cognome_utente);
        return nome;
    }

    public VerticalLayout ImageUtente(){
        VerticalLayout image = new VerticalLayout();
        StreamResource resource = new StreamResource("fotoprofilo",()-> new ByteArrayInputStream(studente.getProfilo().getListaFoto().get(0).getImg()));
        Image img = new Image(resource,"");
        img.getStyle().set("width","200px");
        img.getStyle().set("height","200px");
        image.add(img);
        return image;
    }



    public TextField nome = new TextField("Nome");
    public TextField cognome = new TextField("Cognome");
    public DatePicker compleanno = new DatePicker("Data di nascita");
    public EmailField email = new EmailField("Email");
    public HorizontalLayout Info1(){
        HorizontalLayout info1 = new HorizontalLayout();
        nome.setValue(profilo.getNome());
        nome.setEnabled(false);
        cognome.setValue(profilo.getCognome());
        cognome.setEnabled(false);
        compleanno.setValue(profilo.getDataDiNascita());
        compleanno.setEnabled(false);
        email.setValue(utente.getEmail());
        email.setEnabled(false);
        info1.add(nome,cognome,compleanno,email);
        return info1;
    }

    public NumberField altezza = new NumberField("Altezza (cm)");
    public TextField città = new TextField("Città");
    private Select<String> interessi = new Select<>();
    public TextField luogo_di_nascita = new TextField("Luogo di nascita");
    public HorizontalLayout Info2 (){
        HorizontalLayout info2 = new HorizontalLayout();
        altezza.setValue(profilo.getAltezza());
        altezza.setEnabled(false);
        città.setValue(profilo.getResidenza());
        città.setEnabled(false);
        luogo_di_nascita.setValue(profilo.getLuogoNascita());
        luogo_di_nascita.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
        Interessi [] interess = Interessi.values();
        interessi.setItems(interess[0].toString(),interess[1].toString(),interess[2].toString(),interess[3].toString());
        interessi.setEnabled(false);
        info2.add(altezza,città,luogo_di_nascita,interessi);

        return info2;
    }


    private Select<String> capelli = new Select<>();
    private Select<String> occhi = new Select<>();
    private MultiselectComboBox<String> multiselectComboBox = new MultiselectComboBox();
    public HorizontalLayout Info3(){
        HorizontalLayout info3 = new HorizontalLayout();

        capelli.setLabel("Capelli");
        capelli.setPlaceholder("Colore capelli");
        capelli.setEnabled(false);
        Colori_Capelli [] colore_cap = Colori_Capelli.values();
        capelli.setItems(colore_cap[0].toString(),colore_cap[1].toString(),colore_cap[2].toString(),colore_cap[3].toString(),colore_cap[4].toString(),colore_cap[5].toString(),colore_cap[6].toString());

        occhi.setLabel("Occhi");
        occhi.setPlaceholder("Colore occhi");
        occhi.setEnabled(false);
        Colore_Occhi [] colore_occhi = Colore_Occhi.values();
        occhi.setItems(colore_occhi[0].toString(),colore_occhi[1].toString(),colore_occhi[2].toString(),colore_occhi[3].toString(),colore_occhi[4].toString(),colore_occhi[5].toString(),colore_occhi[6].toString());

        info3.add(capelli,occhi);
        return info3;
    }

    public HorizontalLayout Info4(){
        HorizontalLayout info4 = new HorizontalLayout();
        //Image
        return info4;
    }

    public HorizontalLayout Pulsanti(){
        HorizontalLayout pulsanti = new HorizontalLayout();
        pulsanti.add(Modifica(),Conferma(),CambiaPassword());
        return pulsanti;
    }


    public Button Modifica(){
        Button modifica = new Button("Modifica",buttonClickEvent -> {
            nome.setEnabled(true);
            cognome.setEnabled(true);
            compleanno.setEnabled(true);
            email.setEnabled(true);
            città.setEnabled(true);
            luogo_di_nascita.setEnabled(true);
            occhi.setEnabled(true);
            capelli.setEnabled(true);
            multiselectComboBox.setEnabled(true);
            altezza.setEnabled(true);
            interessi.setEnabled(true);
            multiselectComboBox.setEnabled(true);
        });
        return modifica;
    }

    public Button Conferma(){
        Button conferma = new Button("Conferma", buttonClickEvent -> {
            if(nome.isEmpty()){
                new Notification("Campo Nome vuoto",2000).open();
            }else if(cognome.isEmpty()){
                new Notification("Campo Cognome vuoto",2000).open();
            }else if(compleanno.isEmpty()){
                new Notification("Campo Data di nascita vuoto",2000).open();
            }else if(email.isEmpty()){
                new Notification("Campo Email vuoto",2000).open();
            }else if(altezza.isEmpty()){
                new Notification("Campo Altezza vuoto",2000).open();
            }else if(città.isEmpty()){
                new Notification("Campo vuoto").open();
            }else if(luogo_di_nascita.isEmpty()){
                new Notification("Campo Luogo di nascita vuoto",2000).open();
            }else if(interessi.isEmpty()){
                new Notification("Campo Interessi vuoto",2000).open();
            }else if(capelli.isEmpty()){
                new Notification("Campo Capelli vuoto",2000).open();
            }else if(occhi.isEmpty()){
                new Notification("Campo Occhi vuoto",2000).open();
            }else if(multiselectComboBox.isEmpty()){
                new Notification("Campo Topics vuoto",2000).open();

            }else {
                studente.getProfilo().setAltezza(altezza.getValue());
                studente.getProfilo().setNome(nome.getValue());
                studente.getProfilo().setCognome(cognome.getValue());
                studente.getProfilo().setDataDiNascita(compleanno.getValue());
                studente.setEmail(email.getValue());
                studente.getProfilo().setResidenza(città.getValue());
                studente.getProfilo().setLuogoNascita(luogo_di_nascita.getValue());
                studente.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
                studente.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
                studente.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
                //hobby
                ArrayList<Hobby> hob = new ArrayList<Hobby>();
                for (String s : multiselectComboBox.getValue()) hob.add(Hobby.valueOf(s));
                studente.getProfilo().setHobbyList(hob);

                nome.setEnabled(false);
                cognome.setEnabled(false);
                compleanno.setEnabled(false);
                email.setEnabled(false);
                città.setEnabled(false);
                luogo_di_nascita.setEnabled(false);
                occhi.setEnabled(false);
                capelli.setEnabled(false);
                multiselectComboBox.setEnabled(false);
                altezza.setEnabled(false);
                interessi.setEnabled(false);
                //VEDERE IMMAGINE


                controller.modificaProfilo(studente.getProfilo());
            }

        });
        return conferma;
    }



    public Button CambiaPassword(){
        Button cambia_password = new Button("Cambia password");

        Notification cambio = new Notification();
        cambio.setPosition(Notification.Position.MIDDLE);

        VerticalLayout corpo_notifica = new VerticalLayout();
        corpo_notifica.setAlignItems(Alignment.CENTER);

        Span testo = new Span("Cambia la tua password!");
        PasswordField password_attuale = new PasswordField("Password corrente");
        PasswordField prima_password = new PasswordField("Nuova password");
        PasswordField seconda_password = new PasswordField("Conferma password");
        Button conferma = new Button("Conferma");
        conferma.addClickListener(buttonClickEvent -> {
           if(encoder.matches(password_attuale.getValue(),utente.getPassword())){
               if(prima_password.getValue().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
                   if (prima_password.getValue().equals(seconda_password.getValue())) {
                       utente_controller.cambiaPassword(utente, prima_password.getValue(), password_attuale.getValue());
                       cambio.close();
                   } else {
                       Notification errore_password = new Notification("Le password nuove non corrispondono", 3000, Notification.Position.MIDDLE);
                       errore_password.open();
                   }
               }else {
                   Notification errore_password = new Notification("Le nuova password deve avere minimo 8 carrateri tra cui un nuemro e un carattere speciale", 3000, Notification.Position.MIDDLE);
                   errore_password.open();
               }
           }else {
               Notification errore_password_attuale = new Notification("La password attuale non corrisponde",3000, Notification.Position.MIDDLE);
               errore_password_attuale.open();
           }
        });
        Button annulla = new Button("Annulla");
        annulla.addClickListener(buttonClickEvent -> {
           cambio.close();
        });


        corpo_notifica.add(testo,password_attuale,prima_password,seconda_password,conferma,annulla);
        cambio.add(corpo_notifica);

        cambia_password.addClickListener(buttonClickEvent -> {
            cambio.open();
        });

        return cambia_password;
    }


    private boolean checkMaggiorenne(LocalDate value) {
        return Period.between(value,LocalDate.now()).getYears() >= 18;
    }

/*
    public ProfiloPersonale(GestioneProfiloController controller){
        this.controller = controller;
        setId("personal-profile");
        addClassName("personal-profile");
        setSizeFull();
        add(createSingleUser(studente));
    }

    private VerticalLayout createSingleUser(Studente studente){
        VerticalLayout utente = new VerticalLayout();
        utente.addClassName("card-home");
        utente.setSpacing(false);
        utente.getThemeList().add("spacing-s");

        //First Layout
        HorizontalLayout nome = new HorizontalLayout();
        nome.addClassName("top-layout");
        nome.setSpacing(false);
        nome.getThemeList().add("spacing-s");

        Span nome_utente = new Span(studente.getProfilo().getNome());
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
        ArrayList<Foto> list = (ArrayList<Foto>) registrato.getProfilo().getListaFoto();
        image_profilo.setSrc(list.get(0).getUrl());
        image_profilo.setAlt("Foto non presente");
        image.setId("foto");
        image.add(image_profilo);



        VerticalLayout all_info = new VerticalLayout();

        //info 1
        HorizontalLayout info_uno = new HorizontalLayout();
        TextField name = new TextField("Nome");
        name.setValue(studente.getProfilo().getNome());
        name.setEnabled(false);

        TextField cognome = new TextField("Cognome");
        cognome.setValue(studente.getProfilo().getCognome());
        cognome.setEnabled(false);

        //DATA DA VEDERE PER LA SESSIONE
        DatePicker date = new DatePicker("Data di nascita");
        date.setValue(studente.getProfilo().getDataDiNascita());
        date.setEnabled(false);

        EmailField email_profilo = new EmailField("Email");
        email_profilo.setValue(studente.getEmail());
        email_profilo.setEnabled(false);

        info_uno.add(name,cognome,date,email_profilo);

        //info 2
        HorizontalLayout info_due = new HorizontalLayout();

        NumberField altezza = new NumberField("Altezza (cm)");
        altezza.setValue(studente.getProfilo().getAltezza());
        altezza.setHasControls(true);
        altezza.setStep(1);
        altezza.setMin(150.00);
        altezza.setEnabled(false);

        TextField città = new TextField("Città");
        città.setValue(studente.getProfilo().getResidenza());
        città.setEnabled(false);
        TextField luogo = new TextField("Luogo di nascita");
        luogo.setValue(studente.getProfilo().getLuogoNascita());
        luogo.setEnabled(false);

        info_due.add(altezza,città,luogo);

        //info 3
        HorizontalLayout info_tre = new HorizontalLayout();



        TextField capelli = new TextField("Capelli");
        capelli.setValue(studente.getProfilo().getColori_capelli().toString());
        capelli.setEnabled(false);

        TextField occhi = new TextField("Occhi");
        occhi.setValue(studente.getProfilo().getColore_occhi().toString());
        occhi.setEnabled(false);

        interessi.setLabel("Interessi");
        interessi.setValue(studente.getProfilo().getInteressi().toString());
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

            studente.getProfilo().setAltezza(altezza.getValue());
            studente.getProfilo().setNome(name.getValue());
            studente.getProfilo().setCognome(cognome.getValue());
            studente.getProfilo().setDataDiNascita(date.getValue());
            studente.setEmail(email_profilo.getValue());
            studente.getProfilo().setResidenza(città.getValue());
            studente.getProfilo().setLuogoNascita(luogo.getValue());
            studente.getProfilo().setColore_occhi(Colore_Occhi.valueOf(occhi.getValue()));
            studente.getProfilo().setColori_capelli(Colori_Capelli.valueOf(capelli.getValue()));
            studente.getProfilo().setInteressi(Interessi.valueOf(interessi.getValue()));
            //hobby
            ArrayList<Hobby> hob = new ArrayList<Hobby>();
            for(String s : multiselectComboBox.getValue()) hob.add(Hobby.valueOf(s));
            studente.getProfilo().setHobbyList(hob);

            controller.modificaProfilo(studente.getProfilo(),studente.getPassword());

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


    private Button CheckModifyButton(){

    }*/
}
