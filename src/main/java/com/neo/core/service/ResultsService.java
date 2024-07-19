package com.neo.core.service;

import com.neo.core.entities.UserInfo;
import com.neo.core.entities.results;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ResultsService extends IRootService<results>{
    Page<results> doSearch(
            Integer id,
            Integer id1,
            Pageable paging);
}
