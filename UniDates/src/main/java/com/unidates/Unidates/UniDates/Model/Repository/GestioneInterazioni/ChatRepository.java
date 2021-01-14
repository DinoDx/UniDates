package com.unidates.Unidates.UniDates.Model.Repository.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Chat;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findByMittenteAndDestinatario(Utente mittente, Utente destinatario);
}
