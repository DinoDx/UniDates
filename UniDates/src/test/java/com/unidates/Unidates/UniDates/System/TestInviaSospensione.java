package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
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
public class TestInviaSospensione extends TestBenchTestCase {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    ModerazioneManager moderazioneManager;

    @Autowired
    UserManager userManager;

    @Test
    public void inviaSospensione_valid(){
        ButtonElement apriSospensione = $(ButtonElement.class).id("open-sospensione");
        apriSospensione.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("sospensione")));
        TextFieldElement durata = $(TextFieldElement.class).id("durata");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-sospensione");

        durata.setValue("1");
        dettagli.setValue("dettagli");
        invia.click();
    }

    @Test
    public void inviaSospensione_durataNonValida(){
        ButtonElement apriSospensione = $(ButtonElement.class).id("open-sospensione");
        apriSospensione.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("sospensione")));
        TextFieldElement durata = $(TextFieldElement.class).id("durata");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-sospensione");

        dettagli.setValue("dettagli");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-sospensione")));
        NotificationElement errore = $(NotificationElement.class).id("errore-sospensione");

        assertTrue(errore.isOpen());
    }


    @Test
    public void inviaSospensione_dettagliNonValidi(){
        ButtonElement apriSospensione = $(ButtonElement.class).id("open-sospensione");
        apriSospensione.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("sospensione")));
        TextFieldElement durata = $(TextFieldElement.class).id("durata");
        TextFieldElement dettagli = $(TextFieldElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("invia-sospensione");

        durata.setValue("1");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-sospensione")));
        NotificationElement errore = $(NotificationElement.class).id("errore-sospensione");

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

        CommunityManager s1 = new CommunityManager("communitymanager@gmail.com",passwordEncoder.encode("studenteprova1"));
        s1.setActive(true);
        s1.setProfilo(p1);

        Studente s2 = new Studente("studenteprova2@gmail.com",passwordEncoder.encode("studenteprova2"));
        s2.setActive(true);
        s2.setProfilo(p1);

        utenteRepository.saveAll(Arrays.asList(s1,s2));
        moderazioneManager.inviaSegnalazioneCommunityManager(new Segnalazione(Motivazione.NUDITA, "dettagli2"),userManager.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo().getId());

        getDriver().get("http://localhost:8080/login");

        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("communitymanager@gmail.com");
        passwordFieldElement.setValue("studenteprova1");
        button.click();

        getDriver().get("http://localhost:8080/pannellomoderatore");
    }

    @AfterEach
    public void destroy(){
        getDriver().quit();
    }
}
