package com.unidates.Unidates.UniDates.System;


import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.radiobutton.testbench.RadioButtonGroupElement;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestInvioAmmonimento extends TestBenchTestCase {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    ModerazioneManager moderazioneManager;

    @Autowired
    UserManager userManager;



    @Test
    public void invioAmmonimento_valid(){
        ButtonElement apriAmmonimento = $(ButtonElement.class).id("open-ammonimento");
        apriAmmonimento.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("notifica-ammonimento")));
        NotificationElement notificaAmmonimento = $(NotificationElement.class).id("notifica-ammonimento");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivazione");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-ammonimento");

        motivi.selectByText("VIOLENZA");
        dettagli.setValue("dettagli");
        invia.click();
    }

    @Test
    public void invioAmmonimento_motivazioneNonValida(){
        ButtonElement apriAmmonimento = $(ButtonElement.class).id("open-ammonimento");
        apriAmmonimento.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("notifica-ammonimento")));
        NotificationElement notificaAmmonimento = $(NotificationElement.class).id("notifica-ammonimento");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivazione");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-ammonimento");

        dettagli.setValue("dettagli");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-ammonimento")));
        NotificationElement errore = $(NotificationElement.class).id("errore-ammonimento");

        assertTrue(errore.isOpen());
    }

    @Test
    public void invioAmmonimento_dettagliNonValidi(){
        ButtonElement apriAmmonimento = $(ButtonElement.class).id("open-ammonimento");
        apriAmmonimento.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("notifica-ammonimento")));
        NotificationElement notificaAmmonimento = $(NotificationElement.class).id("notifica-ammonimento");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivazione");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-ammonimento");

        motivi.selectByText("VIOLENZA");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-ammonimento")));
        NotificationElement errore = $(NotificationElement.class).id("errore-ammonimento");

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

        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(img),hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");

        Moderatore s1 = new Moderatore("moderatore@gmail.com",passwordEncoder.encode("studenteprova1"));
        s1.setActive(true);
        s1.setProfilo(p1);

        Studente s2 = new Studente("studenteprova2@gmail.com",passwordEncoder.encode("studenteprova2"));
        s2.setActive(true);
        s2.setProfilo(p1);

        utenteRepository.saveAll(Arrays.asList(s1,s2));
        moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.NUDITA, "dettagli2"),userManager.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo().getId());

        getDriver().get("http://localhost:8080/login");

        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("moderatore@gmail.com");
        passwordFieldElement.setValue("studenteprova1");
        button.click();

        getDriver().get("http://localhost:8080/pannellomoderatore");
    }

    @AfterEach
    public void destroy(){
        getDriver().quit();
    }
}
