package com.neo.core.service;

import com.neo.core.entities.results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ResultsService {
    Page<results> doSearch(
            Integer id1,
            Pageable paging);
}
