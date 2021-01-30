package com.unidates.Unidates.UniDates.View.LoginRegistrazione;


import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "registrazione")
@PageTitle("Registrazione")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/views/registrazione/login.css")
public class RegistrazioneAccount extends FlexLayout {

    private EmailField email;
    private PasswordField password;
    private PasswordField conferma_password;

    public static final String TEXT  = "Iniziamo con alcune informazioni per creare il tuo account personale";


    @Autowired
    GestioneUtentiController gestioneUtentiController;

    private RegistrazioneAccount(){
      addAttachListener(e -> create());
    }

    public void create(){
        setSizeFull();
        setClassName("layout-esterno");
        VerticalLayout layoutDestro = createLayoutDestro();
        VerticalLayout layoutSinistro = createLayoutSinistro();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        add(layoutSinistro, layoutDestro);
    }

    private VerticalLayout createLayoutSinistro() {
        VerticalLayout layoutSinistro = new VerticalLayout();

        VerticalLayout infoInterna = new VerticalLayout();
        infoInterna.add(new H1("Iniziamo!"));
        infoInterna.add(new Span(TEXT));
        infoInterna.setAlignItems(Alignment.CENTER);
        infoInterna.setJustifyContentMode(JustifyContentMode.CENTER);
        infoInterna.setId("info-boxing");

        Image image = new Image();
        image.setId("logo");
        image.setHeight("250px");
        image.setWidth("300px");
        image.setSrc("./images/logos/logo.png");
        layoutSinistro.add(image,infoInterna);
        layoutSinistro.setAlignItems(Alignment.CENTER);
        layoutSinistro.setJustifyContentMode(JustifyContentMode.CENTER);
        return layoutSinistro;
    }

    public VerticalLayout createLayoutDestro(){
        VerticalLayout formRegistrazione = new VerticalLayout();

        FormLayout formFields = createFormFields();

        formRegistrazione.setAlignItems(Alignment.CENTER);
        formRegistrazione.setJustifyContentMode(JustifyContentMode.CENTER);

        formRegistrazione.add(formFields);
        return formRegistrazione;
    }

    private FormLayout createFormFields() {

        FormLayout formFields = new FormLayout();

        email = new EmailField();
        email.setPlaceholder("Inserisci email universitaria");
        email.setLabel("E-mail Universitaria");

        password = new PasswordField();
        password.setLabel("Password(*):");
        password.addFocusListener(event -> {
            new Notification("Inserire una password con un minimo di 8 caratteri, una lettera e un numero.",5000, Notification.Position.MIDDLE).open();
            event.unregisterListener();
        });
        password.setPlaceholder("Inserisci una password");

        conferma_password = new PasswordField();
        conferma_password.setLabel("Conferma Password:");
        conferma_password.setPlaceholder("Conferma password inserita");

        Button reset = new Button("Reset",buttonClickEvent -> {
            email.setValue("");
            password.setValue("");
            conferma_password.setValue("");
        });

        //MESSAGGIO DI ERRORE

        Button prosegui = new Button("Continua con la registrazione",buttonClickEvent -> {

            if(email.isEmpty() || !email.getValue().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
                new Notification("Inserire una email valida!",1000, Notification.Position.MIDDLE).open();

            else if(gestioneUtentiController.isPresentEmail(email.getValue())){
                new Notification("Email già in uso", 2000, Notification.Position.MIDDLE).open();
            }
            else if(password.isEmpty() || conferma_password.isEmpty()){
                new Notification("Inserire una password!",1000, Notification.Position.MIDDLE).open();
            }
            else if(!password.getValue().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
                new Notification("La password inserita non rispetta il formato. \n Inserire una password con un minimo di 8 caratteri, una lettera e un numero!",5000, Notification.Position.MIDDLE).open();
            }
            else if(!password.getValue().equals(conferma_password.getValue())){
                new Notification("Le password non corrispondono!",2000, Notification.Position.MIDDLE).open();
            }
            else {
                StudenteDTO studente = new StudenteDTO();
                studente.setEmail(email.getValue());
                studente.setPassword(password.getValue());
                UI.getCurrent().getSession().setAttribute("utente_reg", studente);
                UI.getCurrent().navigate("registrazione_due");
            }
        });
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(prosegui, reset);


        formFields.add(email,password,conferma_password,buttons);
        return formFields;
    }
}
