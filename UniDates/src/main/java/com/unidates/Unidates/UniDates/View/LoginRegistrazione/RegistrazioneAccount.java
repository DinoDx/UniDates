package com.unidates.Unidates.UniDates.View.LoginRegistrazione;


import com.unidates.Unidates.UniDates.Controller.GestioneUtentiController;
import com.unidates.Unidates.UniDates.DTOs.ProfiloDTO;
import com.unidates.Unidates.UniDates.DTOs.StudenteDTO;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Route(value = "registrazione", layout = MainViewLogin.class)
@PageTitle("Registrazione")
@CssImport("./styles/views/registrazione/registrazione.css")
public class RegistrazioneAccount extends VerticalLayout {

    private EmailField email;
    private PasswordField password;
    private PasswordField conferma_password;

    @Autowired
    GestioneUtentiController gestioneUtentiController;

    private RegistrazioneAccount(){
      addAttachListener(e -> create());
    }

    public void create(){
        setSizeFull();
        setId("Layout-registazione");
        Div formRegistraizioneAccount = createFormRegistrazioneAccount();
        formRegistraizioneAccount.setId("Div-principale");
        setAlignItems(Alignment.CENTER);
        add(formRegistraizioneAccount);
    }

    public Div createFormRegistrazioneAccount(){
        Div formRegistrazione = new Div();

        VerticalLayout formFields = createFormFields();
        HorizontalLayout buttons = createFormButtons();
        formFields.add(buttons);
        buttons.setAlignItems(Alignment.CENTER);
        formFields.setAlignItems(Alignment.CENTER);
        H2 titolo = new H2("Inserisci i dati del tuo account!");
        titolo.setId("titolo-registrazione");
        formRegistrazione.add(titolo,formFields);
        return formRegistrazione;
    }

    private HorizontalLayout createFormButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
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

        buttons.add(prosegui,reset);
        return buttons;
    }

    private VerticalLayout createFormFields() {
        VerticalLayout formFields = new VerticalLayout();
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

        formFields.add(email,password,conferma_password);
        return formFields;
    }
}
