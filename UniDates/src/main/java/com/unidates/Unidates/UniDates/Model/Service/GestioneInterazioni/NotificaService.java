package com.unidates.Unidates.UniDates.Model.Service.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni.NotificaRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificaService {

    private Logger logger = Logger.getLogger("global");
    @Autowired
    private NotificaRepository notificaRepository;

    public void generateNotificaMatch(Studente studente1, Studente studente2){

        Notifica matchNotificaStudente1 = new Notifica(
                studente1,
                studente1.getProfilo().getNome() + " hai un match con " + studente2.getProfilo().getNome() + "!",
                Tipo_Notifica.MATCH,
                studente2.getProfilo().getFotoProfilo()
        );
        matchNotificaStudente1.setEmailToMatchWith(studente2.getEmail());

        Notifica matchNotificaStudente2 = new Notifica(
                studente2,
                studente2.getProfilo().getNome() + " hai un match con " + studente1.getProfilo().getNome() + "!",
                Tipo_Notifica.MATCH,
                studente1.getProfilo().getFotoProfilo()
        );
        matchNotificaStudente2.setEmailToMatchWith(studente1.getEmail());

        // evento notifica

        notificaRepository.save(matchNotificaStudente1);
        notificaRepository.save(matchNotificaStudente2);

        logger.info("Notifica di match pubblicata!");
    }

    public List<Notifica> visualizzaNotifiche(Utente u){
        return  u.getListNotifica();
    }
}
