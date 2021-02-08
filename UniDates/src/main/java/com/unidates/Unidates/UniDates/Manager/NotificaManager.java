package com.unidates.Unidates.UniDates.Manager;

import com.unidates.Unidates.UniDates.Exception.EntityNotFoundException;
import com.unidates.Unidates.UniDates.Model.Entity.*;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Repository.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Repository.FotoRepository;
import com.unidates.Unidates.UniDates.Repository.NotificaRepository;
import com.unidates.Unidates.UniDates.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificaManager {

    @Autowired
    private NotificaRepository notificaRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private AmmonimentiRepository ammonimentiRepository;

    public void generateNotificaMatch(String emailStudente1, String emailStudente2){
        Studente studente1 = (Studente) utenteRepository.findByEmail(emailStudente1);
        Studente studente2 = (Studente) utenteRepository.findByEmail(emailStudente2);

        if(studente1 != null && studente2 != null) {
            Notifica matchNotificaStudente1 = new Notifica(
                    studente1,
                    studente1.getProfilo().getNome() + " hai un match con " + studente2.getProfilo().getNome() + "!",
                    Tipo_Notifica.MATCH,
                    studente2.getProfilo().getFotoProfilo()
                    //Data inserita nel costruttore
            );
            matchNotificaStudente1.setEmailToMatchWith(studente2.getEmail());
            notificaRepository.save(matchNotificaStudente1);


            Notifica matchNotificaStudente2 = new Notifica(
                    studente2,
                    studente2.getProfilo().getNome() + " hai un match con " + studente1.getProfilo().getNome() + "!",
                    Tipo_Notifica.MATCH,
                    studente1.getProfilo().getFotoProfilo()
                    //Data inserita nel costruttore
            );
            matchNotificaStudente2.setEmailToMatchWith(studente1.getEmail());
            notificaRepository.save(matchNotificaStudente2);
        } else throw new EntityNotFoundException("Studenti non trovati!");
    }

    public void genereateNotificaWarning(String studenteEmail, Long removedFotoId){ //DA RICONTROLLARE PER MODERAZIONE

        Studente studente1 = (Studente) utenteRepository.findByEmail(studenteEmail);
        Foto removedfoto =  fotoRepository.findFotoById(removedFotoId);
        if(studente1 != null) {
            if(removedfoto != null) {
                Ammonimento ammonimento = ammonimentiRepository.findByFoto(removedfoto);
                    if(ammonimento != null) {
                        Notifica notificaWarning = new Notifica(
                                studente1,
                                studente1.getProfilo().getNome() + " la tua foto é stata nascosta per violazione dei termini di servizio. Hai ricevuto un ammonimento!",
                                Tipo_Notifica.AMMONIMENTO,
                                removedfoto
                        );
                        notificaWarning.setAmmonimento(ammonimento);
                        notificaRepository.save(notificaWarning);
                    }else throw new EntityNotFoundException("Ammonimento relativo alla foto non trovato!");
             }else throw new EntityNotFoundException("Foto da rimuovere non trovata!");
        } else throw new EntityNotFoundException("Studente non trovato!");
    }



    public void eliminaNotificaMatch(String emailBloccante, String emailBloccato){
        Studente bloccante = (Studente) utenteRepository.findByEmail(emailBloccante);
        Studente bloccato = (Studente) utenteRepository.findByEmail(emailBloccato);

        if(bloccato != null && bloccante != null) {
            Notifica notifica1 = notificaRepository.findByUtenteAndEmailToMatchWith(bloccante, bloccato.getEmail());
            Notifica notifica2 = notificaRepository.findByUtenteAndEmailToMatchWith(bloccato, bloccante.getEmail());
            if(notifica1 != null && notifica2 != null) {
                notificaRepository.delete(notifica1);
                notificaRepository.delete(notifica2);
            } else throw new EntityNotFoundException("Notifica non trovata!");
        }else throw new EntityNotFoundException("Studente non trovato!");
    }
}
