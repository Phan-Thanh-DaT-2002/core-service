package com.neo.core.service;
import com.neo.core.dto.UserInfoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.neo.core.entities.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserInfoService extends IRootService<UserInfo>{
    Page<UserInfoDTO> doSearch(
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
            Pageable paging);

    UserInfo findByUsername(String username);
    UserInfo findByEmail(String email);
    Optional<UserInfoDTO> findByIdUser(Integer id);
}
