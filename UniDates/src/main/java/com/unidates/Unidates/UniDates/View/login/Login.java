package com.unidates.Unidates.UniDates.View.login;



import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;


@Route(value = "login", layout = MainViewLogin.class)
@PageTitle("Login")
@RouteAlias(value = "", layout = MainViewLogin.class)
@CssImport("./styles/views/registrazione/login.css")
public class Login extends VerticalLayout {


    private EmailField email;
    private PasswordField password;
    private Anchor link;
    private Button b;
    private H1 title;

    public Login() {
        setId("login-view");
        title = new H1("Effettua il Login");
        email = new EmailField("Email");
        email.setPlaceholder("user@studenti.unisa.it");
        email.setClearButtonVisible(true);
        email.setErrorMessage("Errore! Inserire un formato valido");


        password = new PasswordField();
        password.setLabel("Password");
        password.setPlaceholder("Inserisci Password");
        password.setValue("");

        link = new Anchor("/registrazione", "Clicca qui per registrarti !");
        link.setId("link");
        b = new Button("Accedi");
        b.setId("accedi");

        //da togliere in seguito
        Anchor anchor = new Anchor("/game");
        anchor.add(b);
        //in seguito togliere ancor e mettere b
        add(title,email,password,link,anchor);


    }
}
