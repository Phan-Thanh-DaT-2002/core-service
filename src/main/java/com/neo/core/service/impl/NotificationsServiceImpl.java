package com.neo.core.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.core.entities.Notifications;
import com.neo.core.repositories.NotificationsRepository;
import com.neo.core.service.NotificationsService;

@Service
public class NotificationsServiceImpl implements NotificationsService{
	
	@Autowired
	NotificationsRepository repo;

	@Override
	public Notifications create(Notifications entity) {
		// TODO Auto-generated method stub
		return repo.save(entity);
	}

	@Override
	public Notifications retrieve(Integer id) {
		// TODO Auto-generated method stub
		Optional<Notifications> entity = repo.findById(id);
		if (!entity.isPresent()) {
			return null;
		}
		return entity.get();
	}

	@Override
	public void update(Notifications entity, Integer id) {
		// TODO Auto-generated method stub
		repo.save(entity);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public List<Notifications> findNotifyByUsername(String username) {
		// TODO Auto-generated method stub
		return repo.findNotiyByUsername(username);
	}

	@Override
	public void seenAll(String username) {
		// TODO Auto-generated method stub
		repo.seenAll(username);
	}
    
}
