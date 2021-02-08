package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Profilo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfiloRepository extends JpaRepository<Profilo, Long> {
    Profilo findProfiloById(Long id);
}
