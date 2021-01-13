package com.unidates.Unidates.UniDates.View.login;



import com.unidates.Unidates.UniDates.View.main.MainViewLogin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.*;



@Route(value = "login", layout = MainViewLogin.class)
@PageTitle("Login")
@RouteAlias(value = "login", layout = MainViewLogin.class)
@CssImport("./styles/views/registrazione/login.css")
public class Login extends VerticalLayout implements BeforeEnterListener {

    private Anchor link;
    private LoginForm login = new LoginForm();

    public Login() {

        //NOTIFICA RECUPERO PASSWORD
        Notification recupero = new Notification();
        recupero.setPosition(Notification.Position.MIDDLE);

        EmailField emailField = new EmailField("Email");
        emailField.getStyle().set("margin-left","2em");

        Button recupero_password = new Button("Recupera password");
        recupero_password.getStyle().set("margin-top","1em");
        recupero_password.getStyle().set("margin-left","2.7em");

        Button annulla_recupero = new Button("Annulla");
        annulla_recupero.addClickListener(buttonClickEvent -> {recupero.close();});
        annulla_recupero.getStyle().set("margin-left","5em");

        recupero.add(new H3("Recupera la tua password!"),emailField,recupero_password,annulla_recupero);

        //DIV DI LOGIN
        addClassName("login-view");
        setId("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setI18n(createLoginI18n());
        login.setId("login");
        login.setAction("login");
        login.addForgotPasswordListener(event -> recupero.open());
        //login.addForgotPasswordListener();
        link = new Anchor("/registrazione" );
        Button registrati = new Button("Clicca qui per registrarti !");
        registrati.setId("pulsante-registrati");
        link.add(registrati);
        link.setId("link");
        add(new H1("Accedi a Unidates"), login,link);
    }


    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setUsername("Email");
        i18n.getForm().setTitle("Login");
        i18n.getForm().setSubmit("Accedi!");
        i18n.getForm().setForgotPassword("Password dimenticata? Recuperala");
        return i18n;
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
