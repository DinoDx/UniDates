package com.unidates.Unidates.UniDates.View.loginRegistrazione;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.web.WebAttributes;


@Route(value = "login")
@PageTitle("Login")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/views/registrazione/login.css")
public class LoginPage extends FlexLayout{

    public static final String TEXT  = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur pharetra magna erat, vel finibus ipsum finibus vel. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. " +
            "Aenean molestie mauris felis, ut convallis mauris bibendum vitae. Etiam mollis sem quis pharetra ullamcorper. Suspendisse faucibus nisi eu libero dapibus tristique." +
            " Praesent elit neque, mollis eu erat sed, imperdiet aliquam justo. Ut efficitur et mi ut pretium. Quisque fringilla,";

    public static final CharSequence ROUTE = "login" ;
    private LoginForm loginForm = new LoginForm();

    String erroreLogin;

    public LoginPage() {
        setId("layout-principale");
        addAttachListener(event -> create());
    }
    public void create(){
        addClassName("layout-esterno");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(createLayoutSinistro(), createLeyoutDestro());
    }

    private VerticalLayout createLayoutSinistro(){
        VerticalLayout layoutSinistro = new VerticalLayout();
        layoutSinistro.setId("layout-sinistro");
        layoutSinistro.setHeight("100%");

        VerticalLayout infoInterna = new VerticalLayout();
        infoInterna.add(new H1("Benvenuto su UniDates!"));
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
    private VerticalLayout createLeyoutDestro(){
        VerticalLayout layoutDestro = new VerticalLayout();
        erroreLogin = (String) VaadinServletRequest.getCurrent().getHttpServletRequest().getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if(erroreLogin != null) {
            switch (erroreLogin){
                case "usernotfound":
                    Notification.show("E-mail o password non validi, é pregato di riprovare!", 3000, Notification.Position.MIDDLE).open();
                    break;
                case "notactiveuser":
                    Notification.show("L'utente con cui si sta provando ad accedere non é stato ancora attivato!", 3000, Notification.Position.MIDDLE).open();
                    break;
                case "banneduser":
                    Notification.show("L'utente con cui si sta provando ad accedere risulta al momento sospeso!", 3000, Notification.Position.MIDDLE).open();
                    break;
                default:
                    Notification.show("Errore generico, riprovare piú tardi!", 3000, Notification.Position.MIDDLE).open();
                    break;
            }
        };

        setSizeFull();
        layoutDestro.setAlignItems(Alignment.CENTER);
        layoutDestro.setJustifyContentMode(JustifyContentMode.CENTER);
        loginForm.setI18n(createLoginI18n());
        loginForm.setId("login");
        loginForm.setAction("login");
        //loginForm.addForgotPasswordListener(event -> createRecuperoPassword().open());
        layoutDestro.add(new H1("Accedi a Unidates"), loginForm, createLinkToRegister());
        return layoutDestro;
    }

    private Anchor createLinkToRegister() {
        Anchor link = new Anchor("/registrazione" );
        Button registrati = new Button("Clicca qui per registrarti !");
        registrati.setId("pulsante-registrati");
        link.add(registrati);
        link.setId("link");

        return link;
    }

   /* private Notification createRecuperoPassword(){
        Notification notificaRecuperoPassword = new Notification();
        notificaRecuperoPassword.setPosition(Notification.Position.MIDDLE);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        Button recupero_password = new Button("Recupera password");
        recupero_password.addClickListener(buttonClickEvent -> {

        } );

        Button annulla_recupero = new Button("Annulla");
        annulla_recupero.addClickListener(buttonClickEvent -> notificaRecuperoPassword.close());

        verticalLayout.add(new H3("Recupera la tua password!"),new EmailField("Email"),recupero_password,annulla_recupero);
        notificaRecuperoPassword.add(verticalLayout);

        return notificaRecuperoPassword;
    } */
    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setUsername("Email");
        i18n.getForm().setForgotPassword(null);
        i18n.getForm().setTitle("Login");
        i18n.getForm().setSubmit("Accedi!");
        return i18n;
    }
}
