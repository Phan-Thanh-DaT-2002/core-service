package com.neo.core.repositories;

import java.util.List;
import java.util.Optional;

import com.neo.core.dto.RolesDTO;
import com.neo.core.entities.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neo.core.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    @Query(value = "SELECT r FROM Roles r WHERE  1=1 AND (?1 IS NULL OR lower(r.idRole) like %?1% OR lower(r.nameRole) like %?1%) ORDER BY r.createdDate desc")
    Page<Roles> doSearch(String keyword, Pageable paging);

    @Query(value = "SELECT r FROM Roles r WHERE 1 = 1 ")
    List<Roles> getListRoles();

//    @EntityGraph(attributePaths = {Roles.Fields.roleCode})
//    public Optional<Roles> findByRoleCode(String roleCode);

	@Query(value = "SELECT r FROM Roles r WHERE r.idRole = ?1")
    public Optional<Roles> findByRoleCode(String roleCode);



}
