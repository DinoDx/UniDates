package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestCancellaProfilo extends TestBenchTestCase {

    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void cancellaProfilo_valid(){
        ButtonElement cancella = $(ButtonElement.class).id("cancella-account");

        cancella.click();

        ButtonElement cancellaConferma = $(ButtonElement.class).id("conferma-cancellazione");
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).id("cancella-account-pw");

        passwordFieldElement.setValue("studenteprova1");
        cancellaConferma.click();

    }
    @Test
    public void cancellaProfilo_passwordErrata(){
        ButtonElement cancella = $(ButtonElement.class).id("cancella-account");

        cancella.click();

        ButtonElement cancellaConferma = $(ButtonElement.class).id("conferma-cancellazione");
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).id("cancella-account-pw");

        passwordFieldElement.setValue("");
        cancellaConferma.click();

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @BeforeEach
    public void SetUpDB(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\WebDriver\\chromedriver.exe");
        setDriver(new ChromeDriver());

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        byte[] img = {1,2,3};

        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(img) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");

        Studente s1 = new Studente("studenteprova1@gmail.com",passwordEncoder.encode("studenteprova1"));
        s1.setActive(true);
        s1.setProfilo(p1);

        utenteRepository.save(s1);
        getDriver().get("http://localhost:8080/login");

        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("studenteprova1@gmail.com");
        passwordFieldElement.setValue("studenteprova1");
        button.click();

        getDriver().get("http://localhost:8080/profilo-personale");
    }

    @AfterEach
    public void destroy(){
        getDriver().quit();
    }

}
