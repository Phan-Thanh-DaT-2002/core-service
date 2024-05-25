package com.neo.core.repositories;

import com.neo.core.entities.results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ResultsRepository extends JpaRepository<results, Integer> {

    @Query(value = "SELECT new com.neo.core.entities.results(r.id, r.userId, r.numberTest, r.score,r.time, r.dayTest,  r.note) " +
            "FROM results r where r.userId = :id1  order by r.numberTest desc"
    )
    Page<results> doSearchResults(
            @Param("id1") Integer id1,
            Pageable paging);
}
