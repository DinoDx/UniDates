package com.unidates.Unidates.UniDates.System;

import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.VerificationToken;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import com.unidates.Unidates.UniDates.Repository.VerificationTokenRepository;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.testbench.CheckboxElement;
import com.vaadin.flow.component.datepicker.testbench.DatePickerElement;
import com.vaadin.flow.component.radiobutton.testbench.RadioButtonGroupElement;
import com.vaadin.flow.component.select.testbench.SelectElement;
import com.vaadin.flow.component.textfield.testbench.NumberFieldElement;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.testbench.UploadElement;
import com.vaadin.testbench.TestBenchTestCase;

import com.vaadin.testbench.elements.CheckBoxGroupElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.notification.testbench.NotificationElement;
import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import org.vaadin.gatanaso.MultiselectComboBox;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TestRegistrazioneAccount extends TestBenchTestCase {

    @Autowired
    UtenteRepository utenteRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void registrazioneAccount_valid() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(2);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("successo-reg")));

        NotificationElement terminata = $(NotificationElement.class).id("successo-reg");
        assertTrue(terminata.isOpen());

    }


    @Test
    public void registrazioneAccount_emailNonValida(){
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("studentecicciobello1");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("notifica-errore")));

        NotificationElement errore = $(NotificationElement.class).id("notifica-errore");
        assertTrue(errore.isOpen());
    }

    @Test
    public void registrazioneAccount_emailGiaRegistrata(){
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("studenteprova1@gmail.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("notifica-errore")));

        NotificationElement errore = $(NotificationElement.class).id("notifica-errore");
        assertTrue(errore.isOpen());
    }

    @Test
    public void registrazioneAccount_nomeNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_cognomeNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_residenzaNonValida() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_luogoNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_numeroNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347jukhu4");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_contattoNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347893532");
        contatto.setValue("belcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooobelcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_interessiNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_minoreDi18anni() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.now());
        capelli.selectItemByIndex(1);
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_capelliNonValidi() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        cognome.setValue("Fasulo");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        occhi.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_occhiNonValidi() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

        nome.setValue("Alessandro");
        residenza.setValue("Salerno");
        luogo.setValue("Nocera");
        numero.setValue("3347838294");
        contatto.setValue("belcontattoooo");
        interessi.selectItemByIndex(1);
        data.setDate(LocalDate.of(1999, 03, 21));
        capelli.selectItemByIndex(1);
        altezza.setValue("180");
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_altezzaNonValida() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_sessiNonValido() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_topicNonValidi() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        sessi.selectByText("UOMO");
        trattamento.setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }

    @Test
    public void registrazioneAccount_fotoNonValida() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);
        trattamento.setChecked(true);


        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }
    @Test
    public void registrazioneAccount_trattamentoNonAccettato() throws IOException {
        TextFieldElement email = $(TextFieldElement.class).id("email-registrazione");
        PasswordFieldElement password = $(PasswordFieldElement.class).id("password-registrazione");
        PasswordFieldElement conf_password = $(PasswordFieldElement.class).id("conferma-password-registrazione");
        ButtonElement continua = $(ButtonElement.class).id("prosegui-button");

        email.setValue("emailimpossibiledaavere@stupidoscemo.com");
        password.setValue("passwordbella99@@");
        conf_password.setValue("passwordbella99@@");

        continua.click();

        waitUntil(ExpectedConditions.urlContains("registrazioneProfilo"));

        TextFieldElement nome = $(TextFieldElement.class).id("nome-reg");
        TextFieldElement cognome = $(TextFieldElement.class).id("cognome-reg");
        TextFieldElement residenza = $(TextFieldElement.class).id("residenza-reg");
        TextFieldElement luogo = $(TextFieldElement.class).id("luogo-reg");
        TextFieldElement numero = $(TextFieldElement.class).id("numero-reg");
        TextFieldElement contatto = $(TextFieldElement.class).id("contatto-reg");


        SelectElement interessi = $(SelectElement.class).id("interessi-reg");
        DatePickerElement data = $(DatePickerElement.class).id("picker-reg");
        SelectElement capelli = $(SelectElement.class).id("capelli-reg");
        SelectElement occhi = $(SelectElement.class).id("occhi-reg");
        NumberFieldElement altezza = $(NumberFieldElement.class).id("altezza-reg");

        RadioButtonGroupElement sessi = $(RadioButtonGroupElement.class).id("sessi-reg");
        List<CheckboxElement> topic = $(CheckboxElement.class).all();
        topic.remove(topic.get(topic.size()-1));
        CheckboxElement trattamento = $(CheckboxElement.class).id("trattamento-reg");
        UploadElement uploadElement = $(UploadElement.class).id("upload-reg");
        ButtonElement conferma = $(ButtonElement.class).id("conferma-reg");

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
        sessi.selectByText("UOMO");
        topic.get(0).setChecked(true);
        topic.get(2).setChecked(true);

        File f = File.createTempFile("TestFileUpload", ".jpg");
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        writer.write("File temporaneo!");
        writer.close();
        f.deleteOnExit();

        uploadElement.upload(f);

        conferma.click();

        waitUntil(ExpectedConditions.presenceOfElementLocated(By.id("errore-reg")));

        NotificationElement errore = $(NotificationElement.class).id("errore-reg");
        assertTrue(errore.isOpen());

    }






    @BeforeEach
    public void populateDB(){

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\WebDriver\\chromedriver.exe");
        setDriver(new ChromeDriver());
        getDriver().get("http://localhost:8080/registrazione");
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
