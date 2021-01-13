package com.unidates.Unidates.UniDates.View.login;


import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
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
public class Registrazione extends VerticalLayout {

    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);


    /*private RadioButtonGroup<String> sessi = new RadioButtonGroup<>();

    private Button prosegui;

    public Registrazione() {
        setId("registrazione");

        HorizontalLayout buttons = new HorizontalLayout();
        FormLayout layout = new FormLayout();
        layout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("8em",1),
                new FormLayout.ResponsiveStep("15em",2),
                new FormLayout.ResponsiveStep("40em",3)
        );

        TextField nome = new TextField("Nome");
        TextField cognome = new TextField("Cognome");
        DatePicker picker = new DatePicker("Data di nascita");

        sessi.setLabel("Il tuo sesso:");
        Sesso[] sess = Sesso.values();
        sessi.setItems(sess[0].toString(),sess[1].toString(),sess[2].toString());
        sessi.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        EmailField email = new EmailField("Email");
        email.setPlaceholder("user@studenti.unisa.it");
        email.setClearButtonVisible(true);
        email.setErrorMessage("Errore! Inserire un formato valido");

        PasswordField password = new PasswordField("Password");
        PasswordField conferma_password = new PasswordField("Conferma Password");
        password.setValue("");
        conferma_password.setValue("");

        layout.add(nome,cognome,email,password,conferma_password,picker);

        Button reset = new Button("Reset",buttonClickEvent -> {
            nome.setValue("");
            cognome.setValue("");
            email.setValue("");
            password.setValue("");
            conferma_password.setValue("");
            picker.setValue(null);
            sessi.setValue(null);
        });




        prosegui = new Button("Continua con la registrazione",buttonClickEvent -> {
            //Sessione
            Profilo profilo = new Profilo();
            profilo.setNome(nome.getValue());
            profilo.setCognome(cognome.getValue());
            profilo.setDataDiNascita(picker.getValue());
            profilo.setSesso(Sesso.valueOf(sessi.getValue()));
            Studente studente = new Studente(email.getValue(),password.getValue(),profilo);
            httpSession.setAttribute("utente_reg", studente);
        });

        link = new Anchor("/registrazione_due");
        link.add(prosegui);

        prosegui.setId("proseguiButton");
        reset.setId("reset");
        link.setId("prosegui");
        buttons.setId("bottoni");

        buttons.add(link,reset);
       add(layout,sessi,buttons);
    }*/

    private Anchor link;

    private Registrazione(){
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
        EmailField email = new EmailField();
        email.setPlaceholder("Inserisci email universitaria");
        email.setLabel("E-mail Universitaria");

        PasswordField password = new PasswordField();
        password.setLabel("Password");
        password.setPlaceholder("Inserisci la password");

        PasswordField conferma_password = new PasswordField();
        conferma_password.setLabel("Conferma Password");
        conferma_password.setPlaceholder("Conferma password inserita");

        //Horizontal Buttons
        HorizontalLayout buttons = new HorizontalLayout();
        Button reset = new Button("Reset",buttonClickEvent -> {
            email.setValue("");
            password.setValue("");
            conferma_password.setValue("");
        });

        Button prosegui = new Button("Continua con la registrazione",buttonClickEvent -> {
            //Sessione
            Studente studente = new Studente(email.getValue(),password.getValue());
            httpSession.setAttribute("utente_reg", studente);
        });
        link = new Anchor("/registrazione_due");
        link.add(prosegui);

        buttons.add(link,reset);
        campi.add(email,password,conferma_password,buttons);
        H2 titolo = new H2("Inserisci i dati del tuo account!");
        titolo.setId("titolo-registrazione");
        registazione.add(titolo,campi);
        return registazione;
    }
}
