package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.VerificationToken;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestLogin extends TestBenchTestCase {

    //Necessaria la licenza di TestBench
    //Settare i webDriver nella cartella corretta

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testLogin_valid(){
        TextFieldElement email = $(TextFieldElement.class).first();
            PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
            ButtonElement button = $(ButtonElement.class).first();
            email.setValue("studenteprova1@gmail.com");
            passwordFieldElement.setValue("studenteprova1");
            button.click();
    }

    @Test
    public void testLogin_utenteBannato(){
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("studenteprova2@gmail.com");
        passwordFieldElement.setValue("studenteprova2");


        button.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/vaadin-vertical-layout/vaadin-login-form")));

        LoginFormElement loginFormElement = $(LoginFormElement.class).first();
        Assertions.assertTrue(loginFormElement.getErrorComponent().isDisplayed());
    }

    @Test
    public void testLogin_utenteNonAttivo(){
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("studenteprova3@gmail.com");
        passwordFieldElement.setValue("studenteprova3");


        button.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/vaadin-vertical-layout/vaadin-login-form")));

        LoginFormElement loginFormElement = $(LoginFormElement.class).first();
        Assertions.assertTrue(loginFormElement.getErrorComponent().isDisplayed());
    }

    @Test
    public void testLogin_utenteNonPresente(){
        TextFieldElement email = $(TextFieldElement.class).first();
        PasswordFieldElement passwordFieldElement = $(PasswordFieldElement.class).first();
        ButtonElement button = $(ButtonElement.class).first();
        email.setValue("studentepro@gmail.com");
        passwordFieldElement.setValue("studenteprova3");

        button.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/vaadin-vertical-layout/vaadin-login-form")));

        LoginFormElement loginFormElement = $(LoginFormElement.class).first();
        Assertions.assertTrue(loginFormElement.getErrorComponent().isDisplayed());
    }


    @BeforeEach
    public void populateDB(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\WebDriver\\chromedriver.exe");
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/" );
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
        s2.setBanned(true);
        s2.setProfilo(p1);
        Studente s3 = new Studente("studenteprova3@gmail.com", passwordEncoder.encode("studenteprova3"));
        s3.setActive(false);
        s3.setProfilo(p1);
        Studente s = new Studente("studenteprovatokenscaduto@gmail.com", passwordEncoder.encode("studenteprovascaduto"));
        s.setActive(false);
        s.setProfilo(p1);
        utenteRepository.saveAll(Arrays.asList(s1,s2,s3,s));

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken("tokenscaduto");
        verificationToken.setUtente(s);
        verificationToken.setExpiryDate(Calendar.getInstance().getTime());
        verificationTokenRepository.save(verificationToken);
    }


    @AfterEach
    public void destroy(){
        getDriver().quit();
    }

}
