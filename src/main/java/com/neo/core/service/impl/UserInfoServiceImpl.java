package com.neo.core.service.impl;
import com.neo.core.dto.UserInfoDTO;
import com.neo.core.entities.UserInfo;
import com.neo.core.repositories.UserInfoRepository;
import com.neo.core.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoRepository repo;

	@Override
	public UserInfo create(UserInfo entity) {
		// TODO Auto-generated method stub
		return repo.save(entity);
	}

    @Override
    public UserInfo retrieve(Integer id) {
        // TODO Auto-generated method stub
        Optional<UserInfo> entity = repo.findById(id);
        return entity.orElse(null);
    }

	@Override
	public void update(UserInfo entity, Integer id) {
		// TODO Auto-generated method stub
		repo.save(entity);
	}

    @Transactional
    @Override
    public void delete(Integer id) throws Exception {
    }

    @Override
    public Page<UserInfoDTO> doSearch(
            String username,
            String email,
            String fullName,
            String phone,
            List<Integer> statusOnline,
            Integer scoreFrom,
            Integer scoreTo,
            String fromDate,
            String toDate,
            Integer id1,
            Pageable paging) {
        if(  statusOnline == null){
            statusOnline = new ArrayList<>();
            statusOnline.addAll(Arrays.asList(1, 2));
        }
        if(  statusOnline.isEmpty()){
            statusOnline.addAll(Arrays.asList(1, 2));
        }

        if(scoreFrom != null){
            scoreFrom = 0;
        }
        if(scoreTo != null){
            scoreTo = 100;
        }

        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        if(fromDate != null){
            dateFrom = LocalDateTime.parse(fromDate);
        }
        if(toDate != null){
            dateTo = LocalDateTime.parse(toDate);
        }
        Integer role = repo.getRoles(id1);
        if(role == 2){
            // trả về các bệnh nhân thuộc bác sĩ đang login quản lý
            return repo.doSearchDoctor(username,email,fullName,phone,statusOnline,scoreFrom,scoreTo,dateFrom,dateTo ,id1,paging);
        }
        else {
            //Trả về tất cả các bản ghi
            return repo.doSearchAll(username,email,fullName,phone,statusOnline,scoreFrom,scoreTo,dateFrom,dateTo,paging);
        }
    }

    @Override
    public UserInfo findByUsername(String username) {
        // TODO Auto-generated method stub
        Optional<UserInfo> entity = repo.findByUsername(username);
        if (!entity.isPresent()) {
            return null;
        }
        return entity.get();
    }

    @Override
    public UserInfo findByEmail(String email) {
        Optional<UserInfo> entity = repo.findByEmail(email);
        if (!entity.isPresent()) {
            return null;
        }
        return entity.get();
    }


    @Override
    public Optional<UserInfoDTO>  findByIdUser(Integer id) {
        return repo.findByIdUser(id);
    }
}
