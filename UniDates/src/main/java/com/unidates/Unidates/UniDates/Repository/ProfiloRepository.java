package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import com.unidates.Unidates.UniDates.Model.Entity.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfiloRepository extends JpaRepository<Profilo, Long> {
    Profilo findProfiloById(Long id);
}
