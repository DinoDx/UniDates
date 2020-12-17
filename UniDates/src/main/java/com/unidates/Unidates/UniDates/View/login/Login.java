package com.unidates.Unidates.UniDates.View.login;



import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.*;


@Route(value = "login", layout = MainViewLogin.class)
@PageTitle("Login")
@RouteAlias(value = "login", layout = MainViewLogin.class)
@CssImport("./styles/views/registrazione/login.css")
public class Login extends VerticalLayout implements BeforeEnterListener {


    private EmailField email;
    private PasswordField password;
    private Anchor link;
    private Button b;
    private H1 title;

    private LoginForm login = new LoginForm();

    public Login() {
        addClassName("login-view");
        setId("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setAction("login");

        add(new H1("Login form prova"), login);
     /*   setId("login-view");
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
        add(title,email,password,link,anchor); */


    }

    /**
     * Callback executed before navigation to attaching Component chain is made.
     *
     * @param event before navigation event with event details
     */
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(event.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")){
            login.setError(true);
        }
    }
}
