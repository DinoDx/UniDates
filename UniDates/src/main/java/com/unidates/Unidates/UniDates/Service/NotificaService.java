package com.unidates.Unidates.UniDates.Service;

import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Repository.NotificaRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificaService {

    private Logger logger = Logger.getLogger("global");

    @Autowired
    private NotificaRepository notificaRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public void generateNotificaMatch(Studente studente1, Studente studente2){

        Notifica matchNotificaStudente1 = new Notifica(
                studente1,
                studente1.getProfilo().getNome() + " hai un match con " + studente2.getProfilo().getNome() + "!",
                Tipo_Notifica.MATCH,
                studente2.getProfilo().getFotoProfilo()
                //Data inserita nel costruttore
        );
        matchNotificaStudente1.setEmailToMatchWith(studente2.getEmail());

        Notifica matchNotificaStudente2 = new Notifica(
                studente2,
                studente2.getProfilo().getNome() + " hai un match con " + studente1.getProfilo().getNome() + "!",
                Tipo_Notifica.MATCH,
                studente1.getProfilo().getFotoProfilo()
                //Data inserita nel costruttore
        );
        matchNotificaStudente2.setEmailToMatchWith(studente1.getEmail());

        // evento notifica

        notificaRepository.save(matchNotificaStudente1);
        notificaRepository.save(matchNotificaStudente2);

        logger.info("Notifica di match pubblicata!");
    }

    public void genereateNotificaWarning(Studente studente1, Foto removedfoto, Ammonimento ammonimento){ //DA RICONTROLLARE PER MODERAZIONE
        Notifica notificaWarning = new Notifica(
                studente1,
                studente1.getProfilo().getNome() + " la tua foto é stata nascosta per violazione dei termini di servizio. Hai ricevuto un ammonimento!",
                Tipo_Notifica.AMMONIMENTO,
                removedfoto
        );
        notificaWarning.setAmmonimento(ammonimento);

        //logica di notifica ammonimento
        notificaRepository.save(notificaWarning);
        logger.info("Notifica di warning pubblicata!");

    }



    public void eliminaNoificaMatch(Studente bloccante, Studente bloccato){

        Notifica notifica1 = notificaRepository.findByUtenteAndEmailToMatchWith(bloccante, bloccato.getEmail());
        Notifica notifica2 = notificaRepository.findByUtenteAndEmailToMatchWith(bloccato, bloccante.getEmail());

        notificaRepository.delete(notifica1);
        notificaRepository.delete(notifica2);
    }
}
