package com.unidates.Unidates.UniDates.Unit;


import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.ProfiloManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.ProfiloRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class TestProfiloManager {


    @Autowired
    ProfiloManager profiloManager;

    @MockBean
    ProfiloRepository profiloRepository;

    @MockBean
    UtenteRepository utenteRepository;

    @MockBean
    FotoRepository fotoRepository;

    @Test
    public void modificaProfilo_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);


            byte[] img = {1,2,3,4};
            Foto foto = new Foto(img);
            foto.setId(1L);


            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");
            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);
            profilo.setId(1L);
            studente.setProfilo(profilo);
            return studente;
        });


        byte[] img = {1,2,3,4};
        Foto foto = new Foto(img);
        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        Profilo p = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto ,hobbyArrayList);
        p.setId(1L);

        assertEquals(p,profiloManager.modificaProfilo("email",p));
    }

    @Test
    public void modificaProfilo_notValid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        assertThrows(EntityNotFoundException.class, ()-> profiloManager.modificaProfilo("email", new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto() ,hobbyArrayList)));
    }


    @Test
    public void setFotoProfilo_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");

            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setFotoProfilo(true);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });

        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            return foto;
        });
        assertTrue(profiloManager.setFotoProfilo("provastudente@gmaill.com",1L).isFotoProfilo());
    }


    @Test
    public void setFotoProfilo_notValid(){
        Mockito.when(fotoRepository.findById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class,() -> profiloManager.setFotoProfilo("email", 1L));
    }



    @Test
    public void aggiungiFotoLista_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");

            byte[] img = {1,2,3};
            Foto foto = new Foto(img);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });


        byte[] img = {1,2,3};
        Foto foto = new Foto(img);
        assertEquals(foto,profiloManager.aggiungiFotoLista("email",foto));
    }


    @Test
    public void aggiungiFotoLista_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.aggiungiFotoLista("email",new Foto()));
    }


    //da controllare
    @Test
    public void aggiungiFotoProfilo_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setFotoProfilo(true);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });


        byte[] img = {1,2,3,4};
        Foto foto = new Foto(img);
        foto.setId(1L);

        Foto f = profiloManager.aggiungiFotoProfilo("sudenteprova1@gmail.com",foto);
        assertTrue(f.isFotoProfilo());
    }

    @Test
    public void aggiungiFotoProfilo_studenteNonTrovato(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        byte[] img = {1,2,3};
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.aggiungiFotoProfilo("email",new Foto(img)));
    }

    @Test
    public void eliminaFotoLista_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });

        Mockito.when(fotoRepository.findFotoById(1L)).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(invocationOnMock.getArgument(0,Long.class));
            return foto;
        });

        byte[] img = {1,2,3};
        Foto foto = new Foto(img);
        foto.setId(1L);
        assertEquals(foto,profiloManager.eliminaFotoLista("email",1L));
    }

    @Test
    public void eliminaFotoLista_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoLista("email",1L));
    }

    @Test
    public void eliminaFotoLista_nonTrovataFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoLista("email",1L));
    }


    @Test
    public void elimnaFotoProfilo_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setFotoProfilo(true);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);

            studente.setProfilo(profilo);
            return studente;
        });

        Studente s = new Studente("studenteprova1@gmail.com","password");
        assertEquals(s,profiloManager.eliminaFotoProfilo("studenteprova1@gmail.com"));
    }


    @Test
    public void eliminaFotoProfilo_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoProfilo("unemailmoltobella@pippo.org"));
    }

    //da controllare
    @Test
    public void eliminaFotoProfilo_nonTrovatoFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {

            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            Studente studente = new Studente(invocationOnMock.getArgument(0,String.class),"password");

            Foto foto = new Foto();
            foto.setFotoProfilo(false);

            Profilo profilo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);
            profilo.removeFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });

        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoProfilo("unemailmoltobella@pippo.org"));
    }


    @Test
    public void findFotoById_valid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(invocationOnMock.getArgument(0,Long.class));
            return foto;
        });

        byte[] img = {1,2,3};
        Foto f = new Foto(img);
        f.setId(1L);

        assertEquals(f,profiloManager.findFotoById(1L));
    }


    @Test
    public void findFotoById_notValid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()->profiloManager.findFotoById(1L));
    }


    //da controllare
    @Test
    public void findProfiloById_valid(){
        Mockito.when(profiloRepository.findProfiloById(anyLong())).thenAnswer(invocationOnMock -> {
            ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
            hobbyArrayList.add(Hobby.ARTE);
            hobbyArrayList.add(Hobby.ANIME);
            hobbyArrayList.add(Hobby.CALCIO);

            byte[] img = {1,2,3};
            Foto foto = new Foto(img);
            foto.setId(1L);
            Profilo p= new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);
            p.setId(invocationOnMock.getArgument(0,Long.class));
            return p;
        });

        ArrayList<Hobby> hobbyArrayList = new ArrayList<Hobby>();
        hobbyArrayList.add(Hobby.ARTE);
        hobbyArrayList.add(Hobby.ANIME);
        hobbyArrayList.add(Hobby.CALCIO);

        byte[] img = {1,2,3};
        Foto foto = new Foto(img);
        foto.setId(1L);
        Profilo oracolo = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,foto,hobbyArrayList);
        oracolo.setId(1L);

        Profilo trovato = profiloManager.findProfiloById(1L);
        assertEquals(oracolo.getId(),trovato.getId());

    }

    @Test
    public void findProfiloById_notValid(){
        Mockito.when(profiloRepository.findProfiloById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()->profiloManager.findFotoById(1L));
    }



}
