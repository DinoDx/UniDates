package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.DTOs.AmmonimentoDTO;
import com.unidates.Unidates.UniDates.DTOs.EntityToDto;
import com.unidates.Unidates.UniDates.DTOs.SegnalazioneDTO;
import com.unidates.Unidates.UniDates.Exception.AlreadyExistException;
import com.unidates.Unidates.UniDates.Manager.MatchManager;
import com.unidates.Unidates.UniDates.Manager.ModerazioneManager;
import com.unidates.Unidates.UniDates.Manager.NotificaManager;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.*;
import com.unidates.Unidates.UniDates.Manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Profile("!test")
@Component
public class Populator implements ApplicationRunner {


    @Autowired
    private UserManager utenteService;

    @Autowired
    private ModerazioneManager moderazioneManager;

    @Autowired
    private MatchManager matchManager;

    @Autowired
    private NotificaManager notificaManager;

    private final String LINK = "https://source.unsplash.com/random";


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Aggiungo 3 utenti

        Studente s1 = new Studente("studenteprova1@gmail.com","studenteprova1");
        s1.setActive(true);

        ArrayList<String> hobbyArrayList = new ArrayList<String>();
        hobbyArrayList.add(Hobby.ARTE.toString());
        hobbyArrayList.add(Hobby.ANIME.toString());
        hobbyArrayList.add(Hobby.CALCIO.toString());


        Profilo p1 = new Profilo("Marco", "Prova1", "Napoli", "Napoli", LocalDate.of(1999,2,10), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.AMBRA, Colore_Occhi.AZZURRI,new Foto(downloadUrl(LINK)) ,hobbyArrayList);
        p1.setNumeroTelefono("3333339900");
        p1.setNickInstagram("marco.prova1");



        Foto f = new Foto(downloadUrl(LINK));

        ArrayList<Hobby> hobbies;
        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SERIE_TV,Hobby.TV,Hobby.VIDEOGIOCHI,Hobby.VIAGGI,Hobby.STORIA,Hobby.CUCINA,Hobby.NATURA,Hobby.FOTOGRAFIA
        }));
        createStudente("marcellabello1@gmail.com","Marcella1@","Marcella","Bello","Napoli","Milano",LocalDate.of(2000,01,01),1.70,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.ARTE,Hobby.MODA
        }));
        createStudente("Caterina2@gmail.com","Caterina2@","Caterina","Cipolla","Salerno","Bolzano",LocalDate.of(2000,01,02),1.60,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.NERI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.MANGA,Hobby.FUMETTI
        }));
        createStudente("Marco3@gmail.com","Marco3@","Marco","Tre","Eboli","Bolzano",LocalDate.of(2000,01,03),1.80,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.ARTE,Hobby.MODA
        }));
        createStudente("Marcello4@gmail.com","Marcello4@","Marcello","Esposito","Napoli","Napoli",LocalDate.of(2000,01,04),1.75,Sesso.UOMO,Interessi.ENTRAMBI, Colori_Capelli.NERI,Colore_Occhi.NERI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.SPORT,Hobby.SERIE_TV,Hobby.TEATRO,Hobby.VIAGGI,Hobby.CUCINA
        }));
        createStudente("Elisabetta5@gmail.com","Elisabetta5@","Elisabetta","Stocastica","Perugia","Ancona",LocalDate.of(2000,01,05),1.61,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.MUSICA,Hobby.ANIME,Hobby.MANGA,Hobby.SERIE_TV,Hobby.VIDEOGIOCHI,Hobby.STORIA
        }));
        createStudente("Elisa6@gmail.com","Elisa6@","Elisa","Bella","Chiavari","Chiavari",LocalDate.of(2000,01,06),1.61,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.ROSSI,Colore_Occhi.CASTANI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.TEATRO,Hobby.VIDEOGIOCHI,Hobby.CUCINA,Hobby.NATURA
        }));
        createStudente("Antonio7@gmail.com","Antonio7@","Antonio","Fagioli","Salerno","Napoli",LocalDate.of(2000,01,07),1.69,Sesso.UOMO,Interessi.UOMINI, Colori_Capelli.NERI,Colore_Occhi.AZZURRI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.ANIME,Hobby.MANGA,Hobby.SERIE_TV,Hobby.TV,Hobby.CUCINA
        }));
        createStudente("Alessandro8@gmail.com","Alessandro8@","Alessandro","Morena","Napoli","Napoli",LocalDate.of(2000,01,28),1.80,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        //398
        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.ARTE,Hobby.MUSICA,Hobby.ANIME,Hobby.MANGA,Hobby.SERIE_TV,Hobby.VIDEOGIOCHI,Hobby.VIAGGI,Hobby.CUCINA
        }));
        createStudente("Martina9@gmail.com","Martina9@","Martina","Fusco","Napoli","Pompei",LocalDate.of(2000,01,10),1.65,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.VERDI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.CALCIO,Hobby.MANGA,Hobby.VIDEOGIOCHI,Hobby.CUCINA
        }));
        createStudente("Raimondo10@gmail.com","Raimondo10@","Raimondo","Mondello","Napoli","Roma",LocalDate.of(2000,01,28),1.76,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.ARTE,Hobby.MUSICA,Hobby.SERIE_TV,Hobby.VIDEOGIOCHI,Hobby.LIBRI,Hobby.FOTOGRAFIA,Hobby.DISEGNO
        }));
        createStudente("Paola11@gmail.com","Paola11@","Paola","Sorrentino","Napoli","Scafati",LocalDate.of(1990,01,10),1.65,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.NERI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.MUSICA,Hobby.SERIE_TV,Hobby.TV,Hobby.VIAGGI,Hobby.FOTOGRAFIA,Hobby.MODA
        }));
        createStudente("Giulia12@gmail.com","Giulia12@","Giulia","Da Vinci","Salerno","Battipaglia",LocalDate.of(2001,01,10),1.65,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.NERI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SERIE_TV,Hobby.TV,Hobby.ARTE,Hobby.POLITICA,Hobby.VIDEOGIOCHI,Hobby.TECNOLOGIA,Hobby.VIAGGI
        }));
        createStudente("Andrea13@gmail.com","Andrea13@","Andrea","Positano","Napoli","Positano",LocalDate.of(1999,10,12),1.64,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.BIONDI,Colore_Occhi.VERDI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.CALCIO,Hobby.ANIME,Hobby.CINEMA,Hobby.TECNOLOGIA,Hobby.TEATRO,Hobby.POLITICA,Hobby.VIDEOGIOCHI,Hobby.STORIA
        }));
        createStudente("Luciano14@gmail.com","Luciano14@","Luciano","Viaggiarulo","Firenze","Firenze",LocalDate.of(1998,03,29),1.90,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.MUSICA,Hobby.SERIE_TV,Hobby.TV,Hobby.VIAGGI,Hobby.LIBRI,Hobby.ARTE,Hobby.CUCINA,Hobby.NATURA,Hobby.DISEGNO,Hobby.MODA,Hobby.ALTRO
        }));
        createStudente("Claudia15@gmail.com","Claudia15@","Claudia","Merlino","Salerno","Salerno",LocalDate.of(2001,05,15),1.74,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.TEATRO,Hobby.VIAGGI,Hobby.FOTOGRAFIA,Hobby.MODA
        }));
        createStudente("Carotta16@gmail.com","Carlotta16@","Carlotta","Zito","Latina","Roma",LocalDate.of(2002,04,10),1.60,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.SPORT,Hobby.CALCIO,Hobby.SERIE_TV,Hobby.TV,Hobby.TEATRO,Hobby.POLITICA,Hobby.VIAGGI,Hobby.STORIA,Hobby.LIBRI,Hobby.CUCINA,Hobby.DISEGNO
        }));
        createStudente("Gianni17@gmail.com","Gianni17@","Gianni","Posa","Viterbo","Savona",LocalDate.of(1988,12,25),1.80,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.VERDI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.SERIE_TV,Hobby.VIDEOGIOCHI,Hobby.TECNOLOGIA,Hobby.VIAGGI,Hobby.STORIA,Hobby.INFORMATICA,Hobby.NATURA,Hobby.FOTOGRAFIA,Hobby.DISEGNO,Hobby.MOTORI
        }));
        createStudente("Dario18@gmail.com","Dario18@","Dario","Palomba","Napoli","Napoli",LocalDate.of(1985,06,18),1.64,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        //408
        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.SPORT
        }));
        createStudente("Lucia19@gmail.com","Lucia19@","Lucia","Morfeo","Battipaglia","Salerno",LocalDate.of(1997,10,10),1.69,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.VERDI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.ANIME,Hobby.MANGA,Hobby.SERIE_TV,Hobby.TV,Hobby.VIDEOGIOCHI,Hobby.TECNOLOGIA,Hobby.INFORMATICA,Hobby.LIBRI
        }));
        createStudente("Francesco20@gmail.com","Francesco20@","Francesco","Posillipo","Napoli","Napoli",LocalDate.of(1993,12,12),1.64,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.SPORT,Hobby.CALCIO,Hobby.SERIE_TV,Hobby.TV,Hobby.ARTE,Hobby.POLITICA,Hobby.VIDEOGIOCHI,Hobby.TECNOLOGIA,Hobby.STORIA,Hobby.INFORMATICA,Hobby.MOTORI,Hobby.ALTRO
        }));
        createStudente("Marco21@gmail.com","Marco21@","Marco","Positano","Napoli","Positano",LocalDate.of(1999,10,12),1.80,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.NERI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.CALCIO,Hobby.SERIE_TV,Hobby.CUCINA,Hobby.MOTORI
        }));
        createStudente("Ivano22@gmail.com","Ivano22@","Ivano","Andria","Salerno","Salerno",LocalDate.of(1980,06,07),1.70,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI,f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.CALCIO,Hobby.VIDEOGIOCHI,Hobby.NATURA,Hobby.MOTORI,Hobby.ALTRO
        }));
        createStudente("Augusto23@gmail.com","Augusto23@","Augusto","Iacuzzo","Salerno","Salerno",LocalDate.of(2001,01,15),1.70,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.AZZURRI,f,hobbies);



        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
               Hobby.MUSICA,Hobby.CINEMA,Hobby.CALCIO, Hobby.SERIE_TV,Hobby.VIAGGI
        }));
        createStudente("Ernesto24@gmail.com","Ernesto24@","Ernesto","San Giovanni","Messina","Catanzaro",LocalDate.of(2001,01,15),1.70,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.AZZURRI,f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CALCIO,Hobby.TECNOLOGIA
        }));
        createStudente("Giovanni25@gmail.com","Giovanni25@","Giovanni","Arienzo","Salerno","Salerno",LocalDate.of(2001,01,15),1.70,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.NERI,Colore_Occhi.VERDI,f,hobbies);

        //da 416 a 441
        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.TEATRO,Hobby.POLITICA
        }));
        createStudente("maria26@gmail.com","maria26","Maria","Acanfora","Napoli","Napoli",LocalDate.of(2000,01,01),1.60,Sesso.DONNA,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.ANIME,Hobby.MANGA, Hobby.FUMETTI, Hobby.SERIE_TV, Hobby.TV, Hobby.ARTE, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.INFORMATICA, Hobby.LIBRI, Hobby.DISEGNO
        }));
        createStudente("mario27@gmail.com","mario27","Mario","Acanfora","Roma","Roma",LocalDate.of(2000,01,01),1.81,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SERIE_TV,Hobby.VIAGGI,Hobby.FOTOGRAFIA
        }));
        createStudente("francesca28@gmail.com","francesca27","Francesca","Bianchi","Bergamo","Bergamo",LocalDate.of(2000,01,01),1.60,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.SPORT,Hobby.CALCIO,Hobby.SERIE_TV,Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.INFORMATICA, Hobby.MOTORI
        }));
        createStudente("marco29@gmail.com","marco28","Marco","Bianchi","Napoli","Napoli",LocalDate.of(2000,01,01),1.70,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SERIE_TV,Hobby.TECNOLOGIA,Hobby.INFORMATICA, Hobby.MOTORI
        }));
        createStudente("francesco30@gmail.com","francesco30","Francesco","D'Auria","Napoli","Napoli",LocalDate.of(2000,01,01),1.72,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.TV,Hobby.ARTE,Hobby.TEATRO, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.INFORMATICA, Hobby.LIBRI, Hobby.FOTOGRAFIA, Hobby.MODA
        }));
        createStudente("francesca31@gmail.com","francesca31","Francesca","Fasulo","Salerno","Salerno",LocalDate.of(2000,01,01),1.50,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.ANIME,Hobby.MANGA,Hobby.FUMETTI,Hobby.SERIE_TV, Hobby.VIAGGI, Hobby.LIBRI
        }));
        createStudente("michela32@gmail.com","michela32","Michela","Sannino","Salerno","Salerno",LocalDate.of(2000,01,01),1.50,Sesso.DONNA,Interessi.ENTRAMBI, Colori_Capelli.BIONDI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.TV,Hobby.ARTE, Hobby.VIAGGI, Hobby.LIBRI, Hobby.CUCINA, Hobby.NATURA
        }));
        createStudente("Paola33@gmail.com","paola33","Paola","Petta","Salerno","Salerno",LocalDate.of(2000,01,01),1.69,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SERIE_TV,Hobby.TV, Hobby.ARTE, Hobby.POLITICA, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.STORIA, Hobby.INFORMATICA, Hobby.LIBRI, Hobby.CUCINA, Hobby.FOTOGRAFIA, Hobby.DISEGNO, Hobby.MOTORI, Hobby.MODA
        }));
        createStudente("Alessandro34@gmail.com","alessandro34","Alessandro","Cuna","Napoli","Napoli",LocalDate.of(2000,01,01),1.75,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SERIE_TV,Hobby.TV, Hobby.DISEGNO
        }));
        createStudente("Daniela35@gmail.com","daniela35","Daniela","Balzano","Salerno","Salerno",LocalDate.of(2000,01,01),1.62,Sesso.DONNA,Interessi.ENTRAMBI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SPORT,Hobby.CALCIO,Hobby.ANIME, Hobby.SERIE_TV, Hobby.ARTE, Hobby.POLITICA, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.MOTORI
        }));
        createStudente("michele36@gmail.com","michele36","Michele","Risi","Napoli","Napoli",LocalDate.of(2000,01,01),1.75,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CINEMA,Hobby.SERIE_TV,Hobby.VIAGGI,Hobby.CUCINA
        }));
        createStudente("genoveffa37@gmail.com","genoveffa37","Genoveffa","Tortora","Salerno","Salerno",LocalDate.of(2000,01,01),1.69,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.CUCINA
        }));
        createStudente("monica38@gmail.com","monica38","Monica","Fusco","Napoli","Salerno",LocalDate.of(2000,01,01),1.62,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SPORT,Hobby.CALCIO, Hobby.SERIE_TV, Hobby.TV, Hobby.POLITICA, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.STORIA, Hobby.INFORMATICA, Hobby.MODA
        }));
        createStudente("fabio39@gmail.com","fabio39","Fabio","Palomba","Napoli","Napoli",LocalDate.of(1996,01,01),1.75,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SPORT,Hobby.CALCIO, Hobby.ANIME, Hobby.MANGA, Hobby.FUMETTI, Hobby.SERIE_TV, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.STORIA, Hobby.INFORMATICA, Hobby.CUCINA, Hobby.NATURA, Hobby.FOTOGRAFIA, Hobby.DISEGNO
        }));
        createStudente("fabio40@gmail.com","fabio40","Fabio","Narducci","Salerno","Salerno",LocalDate.of(1997,01,01),1.74,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.BIONDI,Colore_Occhi.VERDI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA, Hobby.CINEMA, Hobby.ANIME, Hobby.MANGA, Hobby.FUMETTI, Hobby.SERIE_TV, Hobby.ARTE, Hobby.VIAGGI
        }));
        createStudente("maria41@gmail.com","maria41","Maria","Vitiello","Napoli","Salerno",LocalDate.of(2000,01,01),1.62,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA,Hobby.SERIE_TV,Hobby.TV, Hobby.ARTE, Hobby.POLITICA, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.INFORMATICA, Hobby.CUCINA, Hobby.NATURA
        }));
        createStudente("mario42@gmail.com","mario42","Mario","Procida","Lecce","Lecce",LocalDate.of(1996,01,01),1.75,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.BIONDI,Colore_Occhi.AZZURRI, f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SERIE_TV, Hobby.VIDEOGIOCHI, Hobby.VIAGGI, Hobby.STORIA, Hobby.INFORMATICA, Hobby.MOTORI
        }));
        createStudente("edoardo43@gmail.com","edoardo43","Edoardo","Angeloni","Osimo","Osimo",LocalDate.of(1997,01,01),1.74,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA, Hobby.SPORT, Hobby.SERIE_TV, Hobby.VIAGGI
        }));
        createStudente("mariapia44@gmail.com","mariapia44","Maria Pia","Cuciniello","Napoli","Napoli",LocalDate.of(2000,01,01),1.62,Sesso.DONNA,Interessi.ENTRAMBI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.SERIE_TV, Hobby.VIDEOGIOCHI, Hobby.VIAGGI, Hobby.NATURA
        }));
        createStudente("gabriele45@gmail.com","gabriele45","Gabriele","Tuzzo","Palermo","Palermo",LocalDate.of(1999,01,01),1.90,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA, Hobby.SPORT, Hobby.SERIE_TV, Hobby.VIAGGI
        }));
        createStudente("daniela46@gmail.com","daniela46","Daniela","Vasta","Torino","Torino",LocalDate.of(1992,01,01),1.58,Sesso.DONNA,Interessi.ENTRAMBI, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CINEMA, Hobby.CALCIO, Hobby.SERIE_TV, Hobby.TEATRO, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI, Hobby.MOTORI
        }));
        createStudente("michele47@gmail.com","michele47","Michele","Del Deserto","Roma","Roma",LocalDate.of(1999,01,01),1.90,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);


        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.SPORT,Hobby.VIAGGI, Hobby.ALTRO
        }));
        createStudente("andrea48@gmail.com","andrea48","Andrea","Hakimi","Livorno","Livorno",LocalDate.of(1989,01,01),1.90,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.AZZURRI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.VIAGGI
        }));
        createStudente("francesca49@gmail.com","francesca49","Francesca","Pagani","Milano","Milano",LocalDate.of(1992,01,01),1.69,Sesso.DONNA,Interessi.UOMINI, Colori_Capelli.BIONDI,Colore_Occhi.CASTANI, f,hobbies);

        hobbies = new ArrayList<>(Arrays.asList(new Hobby[]{
                Hobby.MUSICA,Hobby.CALCIO, Hobby.ANIME, Hobby.SERIE_TV, Hobby.VIDEOGIOCHI, Hobby.TECNOLOGIA, Hobby.VIAGGI,Hobby.CUCINA
        }));
        createStudente("dario50@gmail.com","dario50","Dario","Moccia","Livorno","Livorno",LocalDate.of(1997,01,01),1.80,Sesso.UOMO,Interessi.DONNE, Colori_Capelli.CASTANI,Colore_Occhi.CASTANI, f,hobbies);


        //Aggiungo un moderatore
        Moderatore m1 = new Moderatore("moderatore@gmail.com", "moderatore");
        Profilo p4 = new Profilo("Marcello", "Moderatore", "Napoli", "Napoli", LocalDate.of(1999,6,12), 170, Sesso.UOMO, Interessi.DONNE, Colori_Capelli.GRIGI, Colore_Occhi.AZZURRI,new Foto(downloadUrl(LINK)),hobbyArrayList );
        p4.addFoto(new Foto(downloadUrl(LINK)), false);
        p4.addFoto(new Foto(downloadUrl(LINK)), false);
        p4.addFoto(new Foto(downloadUrl(LINK)), false);




        //Aggiungo un communityManager

        CommunityManager cm1 = new CommunityManager("communitymanager@gmail.com","communitymanager");
        Profilo p5 = new Profilo("Francesca", "CM", "Napoli", "Napoli", LocalDate.of(1980,7,12), 170, Sesso.DONNA, Interessi.UOMINI, Colori_Capelli.CASTANI, Colore_Occhi.AZZURRI, new Foto(downloadUrl(LINK)),hobbyArrayList);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);
        p5.addFoto(new Foto(downloadUrl(LINK)), false);

        try {
            utenteService.registrazioneStudente(s1, p1); //usati solo per skippare l'invio email di conferma

            utenteService.registrazioneCommunityManager(cm1, p5);
            utenteService.registrazioneModeratore(m1, p4);




            /*matchManager.aggiungiMatch(s1.getEmail(), s2.getEmail());
            matchManager.aggiungiMatch(s2.getEmail(), s1.getEmail());
            notificaManager.generateNotificaMatch(s1.getEmail(), s2.getEmail());
            matchManager.aggiungiMatch(s1.getEmail(), s3.getEmail());
            matchManager.aggiungiMatch(s3.getEmail(), s1.getEmail());
            notificaManager.generateNotificaMatch(s1.getEmail(), s3.getEmail());*/

            moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"),utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
          /*  moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.NUDITA, "dettagli2"),utenteService.trovaStudente(s2.getEmail()).getProfilo().getFotoProfilo().getId());
            moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA , "dettagli3"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
            moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.NUDITA, "dettagli5"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());
            moderazioneManager.inviaSegnalazione(new Segnalazione(Motivazione.VIOLENZA, "dettagli6"),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(2).getId()); */

            moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.CONETNUTO_NON_PERTINENTE, "dettagli1"), m1.getEmail(), s1.getEmail(), utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
            notificaManager.genereateNotificaWarning(s1.getEmail(), utenteService.trovaStudente(s1.getEmail()).getProfilo().getFotoProfilo().getId());
           /* moderazioneManager.inviaAmmonimento(new Ammonimento(Motivazione.SPAM, "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId());
            notificaManager.genereateNotificaWarning(s3.getEmail(), utenteService.trovaStudente(s3.getEmail()).getProfilo().getFotoProfilo().getId()); */

            System.out.println("Populator eseguito correttamente!");
        }
        catch (AlreadyExistException existUserException){
            existUserException.printStackTrace();
        }




       // moderazioneService.inviaAmmonimento(new Ammonimento("motivazione3", "dettagli3"), m1.getEmail(), s3.getEmail(),utenteService.trovaStudente(s3.getEmail()).getProfilo().getListaFoto().get(1).getId());


    }


    public Studente createStudente(String email, String password, String nome, String cognome, String luogodinasciata, String residenza, LocalDate compleanno, Double altezza, Sesso sesso, Interessi interessi, Colori_Capelli capelli, Colore_Occhi occhi, Foto foto, ArrayList<Hobby> hobby){
        Studente studenteCluster = new Studente(email, password);
        studenteCluster.setActive(true);
        ArrayList<String> hobbyList = new ArrayList<>();
        hobby.forEach(hobby1 -> hobbyList.add(hobby1.toString()));
        Profilo profiloCluster = new Profilo(nome,cognome,luogodinasciata,residenza,compleanno,altezza,sesso,interessi,capelli,occhi,foto,hobbyList);
        studenteCluster.setProfilo(profiloCluster);
        utenteService.registrazioneStudente(studenteCluster,profiloCluster);
        return studenteCluster;
    }


    public static byte[] downloadUrl(String stringDownload) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            URL toDownload = new URL(stringDownload);
            byte[] chunk = new byte[4096];
            int bytesRead;
            InputStream stream = toDownload.openStream();
            while ((bytesRead = stream.read(chunk)) > 0) {
                outputStream.write(chunk, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return outputStream.toByteArray();
    }

}
