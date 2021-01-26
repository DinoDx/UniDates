package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Model.Enum.Tipo_Notifica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificaRepository extends JpaRepository<Notifica, Long> {
    Notifica findByUtenteAndEmailToMatchWith(Utente u, String emailToMatchWith);
}
