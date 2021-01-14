package com.unidates.Unidates.UniDates.View.LoginRegistrazione;


import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
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
        setId("Layout-registazione");
        Div principale = new Div(form());
        principale.setId("Div-principale");
        setAlignItems(Alignment.CENTER);
        add(principale);
    }

    public Div form(){
        Div registazione = new Div();
        VerticalLayout campi = new VerticalLayout();
        campi.setAlignItems(Alignment.CENTER);

        //Vertical fields
        email = new EmailField();
        email.setPlaceholder("Inserisci email universitaria");
        email.setLabel("E-mail Universitaria");

        password = new PasswordField();
        password.setLabel("Password");
        password.setPlaceholder("Inserisci la password");



        conferma_password = new PasswordField();
        conferma_password.setLabel("Conferma Password");
        conferma_password.setPlaceholder("Conferma password inserita");

        //Horizontal Buttons
        HorizontalLayout buttons = new HorizontalLayout();
        Button reset = new Button("Reset",buttonClickEvent -> {
            email.setValue("");
            password.setValue("");
            conferma_password.setValue("");
        });

        //MESSAGGIO DI ERRORE
        Dialog dialog = new Dialog();
        dialog.add(new Text("Le password non corrispondono, riprova!"));
        dialog.setWidth("200px");
        dialog.setHeight("150px");

        Button prosegui = new Button("Continua con la registrazione",buttonClickEvent -> {
            //Sessione
            String prima_password = password.getValue();
            String seconda_password = conferma_password.getValue();

            if(email.isEmpty()){
                Notification errore_email = new Notification("Inserire una email valida!",1000, Notification.Position.MIDDLE);
                errore_email.open();
            }
            else if(prima_password.equals(seconda_password) && (!prima_password.isEmpty() || !seconda_password.isEmpty())) {
                Studente studente = new Studente(email.getValue(), password.getValue());
                httpSession.setAttribute("utente_reg", studente);
                UI.getCurrent().navigate("registrazione_due");
            }
                else if((prima_password.isEmpty() && seconda_password.isEmpty()) || !prima_password.equals(seconda_password)){
                    dialog.open();
                }
                    else{
                        dialog.open();
                    }

        });
        buttons.add(prosegui,reset);
        campi.add(email,password,conferma_password,buttons);

        H2 titolo = new H2("Inserisci i dati del tuo account!");
        titolo.setId("titolo-registrazione");
        registazione.add(titolo,campi);
        return registazione;
    }
}
