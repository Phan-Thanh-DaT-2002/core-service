package com.neo.core.service.impl;


import com.google.gson.Gson;
import com.neo.core.entities.Roles;
import com.neo.core.repositories.RolesRepository;
import com.neo.core.repositories.UserInfoRepository;
import com.neo.core.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImpl implements RolesService {
	
	@Autowired
	RolesRepository repo;
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public Roles create(Roles entity) {
		// TODO Auto-generated method stub
		return repo.save(entity);
	}

	@Override
	public Roles retrieve(Integer id) {
		// TODO Auto-generated method stub
		Optional<Roles> entity = repo.findById(id);
		if (!entity.isPresent()) {
			return null;
		}
		return entity.get();
	}

	@Override
	public void update(Roles entity, Integer id) {
		// TODO Auto-generated method stub
		repo.save(entity);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public Page<Roles> doSearch(String keyword, Pageable paging) {
		// TODO Auto-generated method stub
		return repo.doSearch(keyword, paging);
	}

	@Override
	public Roles findByRoleCode(String roleCode) {
		// TODO Auto-generated method stub
		Optional<Roles> entity = repo.findByRoleCode(roleCode);
		if (!entity.isPresent()) {
			return null;
		}
		return entity.get();
	}

	@Override
	public List<Roles> getListRoles() {
		// TODO Auto-generated method stub
		return repo.getListRoles();
	}

	@Override
	public Roles deleteRole(Integer id) throws Exception {
		Optional<Roles> optional = repo.findById(id);
		if (optional.isPresent()) {
//			List<UserInfo> userInfos= userInfoRepository.findByRoleId(id);
//			if(userInfos != null && userInfos.size()>0){
//				return null;
//			}
			Roles roles = optional.get();
//			roles.setDelete(DELETED);
//			roles.setUndoStatus(ConstantDefine.STATUS.CAN_BE_UNDO);
			return repo.save(roles);
		} else {
			throw new Exception();
		}
	}
    

	@Override
	public boolean deleteRoles(Integer[] ids, Gson g, String createdBy, HttpServletRequest request) throws Exception {
		for (Integer id : ids) {
			Roles entity = deleteRole(id);
			if (entity == null) {
				return false;
			}

		}
		return true;
	}
}
