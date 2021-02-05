package com.unidates.Unidates.UniDates.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmmonimentiRepository extends JpaRepository<Ammonimento, Long> {
    Ammonimento findByFoto(Foto foto);
}
