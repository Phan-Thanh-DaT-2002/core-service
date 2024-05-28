package com.neo.core.service.impl;

import com.neo.core.entities.results;
import com.neo.core.repositories.ResultsRepository;
import com.neo.core.service.ResultsService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ResultsServiceImpl implements ResultsService {

    @Autowired
    ResultsRepository repo;


    public Page<results> doSearch(
            Integer id,
            Integer id1,
            Pageable paging){
        Integer nextId = id;
        if(id == null ) {
            nextId = id1;
        }
        return repo.doSearchResults(nextId,paging);
    }
}
