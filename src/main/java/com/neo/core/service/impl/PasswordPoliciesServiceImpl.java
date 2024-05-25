package com.neo.core.service.impl;


import com.neo.core.entities.PasswordPolicies;
import com.neo.core.repositories.PasswordPoliciesRepository;
import com.neo.core.service.PasswordPoliciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordPoliciesServiceImpl implements PasswordPoliciesService{
	
	@Autowired
	PasswordPoliciesRepository repo;

	@Override
	public PasswordPolicies create(PasswordPolicies entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PasswordPolicies retrieve(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(PasswordPolicies entity, Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PasswordPolicies getPasswordPolicy() {
		// TODO Auto-generated method stub
		List<PasswordPolicies> listPolicy = repo.findAll();
		if(listPolicy != null && !listPolicy.isEmpty()) {
			return listPolicy.get(0);
		}
		return null;
	}

	
}
