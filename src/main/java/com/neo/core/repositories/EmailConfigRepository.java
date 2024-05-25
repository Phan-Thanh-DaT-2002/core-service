package com.neo.core.repositories;

import com.neo.core.dto.EmailToDTO;
import com.neo.core.entities.EmailConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmailConfigRepository extends JpaRepository<EmailConfig, Integer> {
    @Query(value = "SELECT u FROM EmailConfig u order by u.id")
    Page<EmailConfig> doSearch(Pageable paging);
}
