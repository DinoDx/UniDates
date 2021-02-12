package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.radiobutton.testbench.RadioButtonGroupElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextAreaElement;
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
public class TestInvioSegnalazione extends TestBenchTestCase {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UtenteRepository utenteRepository;

    @Test
    public void inviaSegnalazione_valid(){
        ButtonElement segnala = $(ButtonElement.class).id("report-button");
        segnala.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("report")));
        NotificationElement report = $(NotificationElement.class).id("report");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivi");
        TextAreaElement dettagli = $(TextAreaElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("send-report");

        motivi.selectByText("VIOLENZA");
        dettagli.setValue("dettagli");
        invia.click();
    }

    @Test
    public void inviaSegnalazione_motivazioneNonValida(){
        ButtonElement segnala = $(ButtonElement.class).id("report-button");
        segnala.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("report")));
        NotificationElement report = $(NotificationElement.class).id("report");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivi");
        TextAreaElement dettagli = $(TextAreaElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("send-report");

        dettagli.setValue("dettagli");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-report")));
        NotificationElement errore = $(NotificationElement.class).id("errore-report");

        assertTrue(errore.isOpen());
    }

    @Test
    public void inviaSegnalazione_dettagliNonValidi(){
        ButtonElement segnala = $(ButtonElement.class).id("report-button");
        segnala.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("report")));
        NotificationElement report = $(NotificationElement.class).id("report");
        RadioButtonGroupElement motivi = $(RadioButtonGroupElement.class).id("motivi");
        TextAreaElement dettagli = $(TextAreaElement.class).id("dettagli");
        ButtonElement invia = $(ButtonElement.class).id("send-report");

        motivi.selectByText("VIOLENZA");
        invia.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-report")));
        NotificationElement errore = $(NotificationElement.class).id("errore-report");

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
        Studente s2 = new Studente("studenteprova2@gmail.com",passwordEncoder.encode("studenteprova2"));
        s2.setActive(true);
        s2.setProfilo(p1);

        utenteRepository.saveAll(Arrays.asList(s1,s2));
        getDriver().get("http://localhost:8080/login");

        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("studenteprova1@gmail.com");
        passwordFieldElement.setValue("studenteprova1");
        button.click();

        getDriver().get("http://localhost:8080/");
    }

    @AfterEach
    public void destroy(){
        getDriver().quit();
    }
}
