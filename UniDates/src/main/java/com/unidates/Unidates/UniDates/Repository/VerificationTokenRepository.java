package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Utente;
import com.unidates.Unidates.UniDates.Model.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUtente(Utente utente);
}
