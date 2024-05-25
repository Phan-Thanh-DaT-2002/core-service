package com.neo.core.service;

import com.google.gson.Gson;
import com.neo.core.entities.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public interface RolesService extends IRootService<Roles>{
    Page<Roles> doSearch(String keyword, Pageable paging);
    Roles findByRoleCode(String roleCode);
    List<Roles> getListRoles();

    Roles deleteRole(Integer id) throws Exception;
    boolean deleteRoles(Integer[] ids, Gson g, String createdBy, HttpServletRequest request) throws Exception;
}
