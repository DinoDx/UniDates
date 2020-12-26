package com.unidates.Unidates.UniDates.View.login;


import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "registrazione", layout = MainViewLogin.class)
@PageTitle("Registrazione")
@CssImport("./styles/views/registrazione/registrazione.css")
public class Registrazione extends VerticalLayout {

    //ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    //HttpSession httpSession = servletRequestAttributes.getRequest().getSession(true);


    private RadioButtonGroup<String> sessi = new RadioButtonGroup<>();
    private Anchor link;
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
        sessi.setItems("Uomo", "Donna", "Altro");
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


        //httpSession.setAttribute("utente", new Studente(email.getValue(), password.getValue(), null));


        prosegui = new Button("Continua con la registrazione");

        link = new Anchor("/registrazione_due");
        link.add(prosegui);

        prosegui.setId("b");
        reset.setId("reset");
        link.setId("prosegui");
        buttons.setId("bottoni");

        buttons.add(link,reset);
        add(layout,sessi,buttons);
    }

}
