package com.neo.core.service.impl;

import com.neo.core.entities.results;
import com.neo.core.repositories.ResultsRepository;
import com.neo.core.service.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ResultsServiceImpl implements ResultsService {

    @Autowired
    ResultsRepository repo;
    @Override
    public results create(results  entity) {
        // TODO Auto-generated method stub
        return repo.save(entity);
    }




    @Override
    public results retrieve(Integer id) {
        // TODO Auto-generated method stub
        Optional<results> entity = repo.findById(id);
        return entity.orElse(null);
    }

    @Override
    public void update(results entity, Integer id) {
        // TODO Auto-generated method stub
        repo.save(entity);
    }

    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
    }

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
