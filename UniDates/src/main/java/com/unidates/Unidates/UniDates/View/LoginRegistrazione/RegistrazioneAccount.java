package com.unidates.Unidates.UniDates.View.LoginRegistrazione;


import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Route(value = "registrazione", layout = MainViewLogin.class)
@PageTitle("Registrazione")
@CssImport("./styles/views/registrazione/registrazione.css")
public class RegistrazioneAccount extends VerticalLayout {

    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);

    private EmailField email;
    private PasswordField password;
    private PasswordField conferma_password;

    private RegistrazioneAccount(){
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
        Dialog passwordNotMatchDialog = new Dialog();
        passwordNotMatchDialog.setCloseOnOutsideClick(false);
        passwordNotMatchDialog.add(new Text("Le password non corrispondono, riprova!"));
        passwordNotMatchDialog.setWidth("200px");
        passwordNotMatchDialog.setHeight("150px");

        Button prosegui = new Button("Continua con la registrazione",buttonClickEvent -> {

            if(email.isEmpty() || !email.getValue().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"))
                new Notification("Inserire una email valida!",1000, Notification.Position.MIDDLE).open();
            else if( password.isEmpty() || conferma_password.isEmpty() || !password.getValue().equals(conferma_password.getValue()) || !password.getValue().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")){
                passwordNotMatchDialog.open();
            }
            else {
                Studente studente = new Studente(email.getValue(), password.getValue());
                httpSession.setAttribute("utente_reg", studente);
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
            Dialog passwordInfoDialog = new Dialog();
            passwordInfoDialog.setCloseOnOutsideClick(true);
            passwordInfoDialog.add(new Text("(*)La password deve avere un minimo di 8 caratteri, un numero ed un carattere speciale"));
            passwordInfoDialog.setWidth("200px");
            passwordInfoDialog.setHeight("200px");
            passwordInfoDialog.open();
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
