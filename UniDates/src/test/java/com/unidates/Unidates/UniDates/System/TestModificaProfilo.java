package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.checkbox.testbench.CheckboxElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.radiobutton.testbench.RadioButtonGroupElement;
import com.vaadin.flow.component.select.testbench.SelectElement;
import com.vaadin.flow.component.textfield.testbench.NumberFieldElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.flow.component.upload.testbench.UploadElement;
import com.vaadin.testbench.TestBenchTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestModificaProfilo extends TestBenchTestCase {

    @Autowired
    UtenteRepository utenteRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void modificaProfilo_valid() {
        TextFieldElement nome = $(TextFieldElement.class).id("nome-mod");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-mod");
        TextFieldElement residenza = $(TextFieldElement.class).id("citta-mod");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-mod");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-mod");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-mod");


        SelectElement interessi = $(SelectElement.class).id("interessi-mod");
        DatePickerElement data = $(DatePickerElement.class).id("compleanno-mod");
        SelectElement capelli = $(SelectElement.class).id("capelli-mod");
        SelectElement occhi = $(SelectElement.class).id("occhi-mod");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-mod");

        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));


        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);

        conferma.click();

        waitUntil(ExpectedConditions.urlContains("profilo-personale"));



    }

    @Test
    public void aggiungiFoto_valid() throws IOException {
        UploadElement uploadElement = $(UploadElement.class).id("upload-mod");
        ButtonElement insertFotoElement = $(ButtonElement.class).id("insert-button-mod");

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);
        insertFotoElement.click();
    }

    @Test
    public void aggiungiFoto_formatoNonValido(){
        ButtonElement insertFotoElement = $(ButtonElement.class).id("insert-button-mod");

        insertFotoElement.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement error = $(NotificationElement.class).id("errore-mod");
        assertTrue(error.isOpen());
    }


    @Test
    public void modificaProfilo_nomeNonValido(){
        TextFieldElement nome = $(TextFieldElement.class).id("nome-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        nome.setValue("");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @Test
    public void modificaProfilo_cognomeNonValido(){
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        cognome.setValue("");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @Test
    public void modificaProfilo_residenzaNonValida(){
        TextFieldElement residenza = $(TextFieldElement.class).id("citta-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        residenza.setValue("");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @Test
    public void modificaProfilo_luogoNonValida(){
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        luogo.setValue("");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }
    @Test
    public void modificaProfilo_numeroNonValido(){
        TextFieldElement numero = $(TextFieldElement.class).id("numero-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        numero.setValue("3909393939939393939");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @Test
    public void modificaProfilo_contattoNonValido(){
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        contatto.setValue("contattobellobellobellocontattobellobellobellocontattobellobellobellocontattobellobellobellocontattobellobellobellocontattobellobellobellocontattobellobellobello");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }

    @Test
    public void modificaProfilo_dataNonValida(){
        DatePickerElement data = $(DatePickerElement.class).id("compleanno-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        data.setDate(LocalDate.now());

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

        NotificationElement errore = $(NotificationElement.class).id("errore-mod");
        assertTrue(errore.isOpen());
    }


    @Test
    public void modificaProfilo_altezzaNonValida(){
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-mod");

        ButtonElement conferma = $(ButtonElement.class).id("conferma-mod-button");
        ButtonElement modificaProfilo = $(ButtonElement.class).id("modifica-button-mod");


        modificaProfilo.click();
        altezza.setValue("");

        conferma.click();
        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-mod")));

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
