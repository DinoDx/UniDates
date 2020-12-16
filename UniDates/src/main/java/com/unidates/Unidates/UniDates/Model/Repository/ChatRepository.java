package com.unidates.Unidates.UniDates.Model.Repository;

import com.unidates.Unidates.UniDates.Model.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
