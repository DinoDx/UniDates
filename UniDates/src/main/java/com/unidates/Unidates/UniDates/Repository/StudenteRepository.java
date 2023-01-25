package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudenteRepository extends JpaRepository<Studente, String> {
    Studente findByProfilo(Profilo profilo);
}
