package com.unidates.Unidates.UniDates.Unit;


import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Manager.ProfiloManager;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
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
            ArrayList<Foto> foto = new ArrayList<Foto>();
            foto.add(new Foto());

            Studente studente = new Studente();
            Profilo profilo = new Profilo();
            profilo.setId(1L);
            profilo.setListaFoto(foto);
            profilo.setStudente(studente);
            studente.setProfilo(profilo);
            return studente;
        });

        Profilo p = new Profilo();
        p.setId(1L);

        assertEquals(p,profiloManager.modificaProfilo("email",p));
    }

    @Test
    public void modificaProfilo_notValid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.modificaProfilo("email",new Profilo()));
    }


    @Test
    public void setFotoProfilo_valid(){

    }


    @Test
    public void setFotoProfilo_notValid(){
        Mockito.when(fotoRepository.findById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class,() -> profiloManager.setFotoProfilo("email", 1L));
    }


    //da controllare
    @Test
    public void aggiungiFotoLista_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();

            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));

            Profilo profilo = new Profilo();
            //Foto f = new Foto();
            //byte[] img = {1,2,3,4};
            //f.setImg(img);
            Foto f = new Foto();
            f.setId(1L);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });

        Foto f = new Foto();
        f.setId(1L);
        assertEquals(f,profiloManager.aggiungiFotoLista("email",new Foto()));
    }


    @Test
    public void aggiungiFotoLista_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.aggiungiFotoLista("email",new Foto()));
    }


    //da controllare
    @Test
    public void aggiungiFotoLista_FotoGiaPresente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();

            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));

            Profilo profilo = new Profilo();
            Foto f = new Foto();
            byte[] img = {1,2,3,4};
            f.setImg(img);
            f.setId(1L);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.aggiungiFotoLista("email",new Foto()));
    }


    @Test
    public void eliminaFotoLista_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();
            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));
            Profilo profilo = new Profilo();
            Foto f = new Foto();
            f.setId(1L);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });

        Mockito.when(fotoRepository.findFotoById(1L)).thenAnswer(invocationOnMock -> {
           Foto f = new Foto();
           f.setId(invocationOnMock.getArgument(0,Long.class));
           return f;
        });

        Foto f = new Foto();
        f.setId(1L);
        assertEquals(f,profiloManager.eliminaFotoLista("email",1L));
    }

    @Test
    public void eliminaFotoLista_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoLista("email",1L));
    }

    @Test
    public void eliminaFotoLista_nonTrovataFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();
            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));
            Profilo profilo = new Profilo();
            Foto f = new Foto();
            f.setId(1L);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoLista("email",1L));
    }


    @Test
    public void elimnaFotoProfilo_valid(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();
            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));
            Profilo profilo = new Profilo();
            Foto f = new Foto();
            f.setFotoProfilo(true);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });

        Studente s = new Studente();
        s.setEmail("email");

        assertEquals(s,profiloManager.eliminaFotoProfilo("email"));
    }


    @Test
    public void eliminaFotoProfilo_nonTrovatoStudente(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoProfilo("email"));
    }

    @Test
    public void eliminaFotoProfilo_nonTrovatoFoto(){
        Mockito.when(utenteRepository.findByEmail(anyString())).thenAnswer(invocationOnMock -> {
            ArrayList<Foto> foto = new ArrayList<>();
            Studente studente = new Studente();
            studente.setEmail(invocationOnMock.getArgument(0,String.class));
            Profilo profilo = new Profilo();
            Foto f = new Foto();
            f.setFotoProfilo(false);

            foto.add(f);
            profilo.setListaFoto(foto);
            studente.setProfilo(profilo);
            return studente;
        });
        assertThrows(EntityNotFoundException.class, ()-> profiloManager.eliminaFotoProfilo("email"));
    }


    @Test
    public void findFotoById_valid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenAnswer(invocationOnMock -> {
            Foto foto = new Foto();
            foto.setId(invocationOnMock.getArgument(0,Long.class));
            return foto;
        });

        Foto f = new Foto();
        f.setId(1L);

        assertEquals(f,profiloManager.findFotoById(1L));
    }


    @Test
    public void findFotoById_notValid(){
        Mockito.when(fotoRepository.findFotoById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()->profiloManager.findFotoById(1L));
    }


    @Test
    public void findProfiloById_valid(){
        Mockito.when(profiloRepository.findProfiloById(anyLong())).thenAnswer(invocationOnMock -> {
            Profilo p = new Profilo();
            p.setId(invocationOnMock.getArgument(0,Long.class));
            return p;
        });

        Profilo p = new Profilo();
        p.setId(1L);

        assertEquals(p,profiloManager.findProfiloById(1L));

    }

    @Test
    public void findProfiloById_notValid(){
        Mockito.when(profiloRepository.findProfiloById(anyLong())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()->profiloManager.findFotoById(1L));
    }



}
