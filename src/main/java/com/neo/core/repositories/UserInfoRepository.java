package com.neo.core.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.neo.core.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.neo.core.entities.UserInfo;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query(value = "SELECT new com.neo.core.dto.UserInfoDTO(u.id, u.username, u.password, u.fullName, u.dob, u.gender, " +
            "u.roles, ro.nameRole, u.phone,u.email, u.address, u.createdDate, u.statusOnline,u.idPeerjs,u.idDoctor,u.status, re.score, u1.fullName) " +
            "FROM UserInfo u " +
            "left join Roles ro on u.roles = ro.idRole " +
            "left join results re on (re.userId = u.id  and re.numberTest  = (SELECT MAX(re1.numberTest) FROM results re1 where re.userId = re1.userId  ) )" +
            "left join UserInfo u1 on u1.id = u.idDoctor " +
            " where u.status in (1,2)  " +
            "AND (TRIM(:username) IS NULL OR LOWER(u.username) like LOWER(CONCAT('%',TRIM(:username), '%')))\n "+
            "AND (TRIM(:email) IS NULL OR LOWER(u.email) like LOWER(CONCAT('%',TRIM(:email), '%'))) " +
            "AND (TRIM(:fullName) IS NULL OR LOWER(u.fullName) like LOWER(CONCAT('%',TRIM(:fullName), '%'))) " +
            "AND (TRIM(:phone) IS NULL OR LOWER(u.phone) like LOWER(CONCAT('%',TRIM(:phone), '%'))) " +
            "AND ( u.statusOnline in (:statusOnline)) " +
            "AND ( u.status in (:status)) " +
            "AND (:scoreFrom IS NULL OR re.score >= :scoreFrom) " +
            "AND (:scoreTo IS NULL OR re.score <= :scoreTo) " +
            "AND (:dateFrom IS NULL OR u.createdDate >= :dateFrom) " +
            "AND (:dateTo IS NULL OR u.createdDate <= :dateTo) "
    )
    Page<UserInfoDTO> doSearchAll(
                    @Param("username") String username,
                    @Param("email") String email,
                    @Param("fullName") String fullName,
                    @Param("phone") String phone,
                    @Param("statusOnline") List<Integer> statusOnline,
                    @Param("status") List<Integer> status,
                    @Param("scoreFrom") Integer scoreFrom,
                    @Param("scoreTo") Integer scoreTo,
                    @Param("dateFrom") LocalDateTime dateFrom,
                    @Param("dateTo") LocalDateTime dateTo,
                    Pageable paging);


    @Query(value = "SELECT new com.neo.core.dto.UserInfoDTO(u.id, u.username, u.password, u.fullName, u.dob, u.gender, " +
            "u.roles, ro.nameRole, u.phone,u.email, u.address, u.createdDate, u.statusOnline,u.idPeerjs,u.idDoctor,u.status, re.score, u1.fullName) " +
            "FROM UserInfo u " +
            "left join Roles ro on u.roles = ro.idRole " +
            "left join results re on (re.userId = u.id  and re.numberTest  = (SELECT MAX(re1.numberTest) FROM results re1 where re.userId = re1.userId  ) )" +
            "left join UserInfo u1 on u1.id = u.idDoctor " +
            " where u.status =1  and u.idDoctor = :id1 " +
            "AND (TRIM(:username) IS NULL OR LOWER(u.username) like LOWER(CONCAT('%',TRIM(:username), '%')))\n "+
            "AND (TRIM(:email) IS NULL OR LOWER(u.email) like LOWER(CONCAT('%',TRIM(:email), '%'))) " +
            "AND (TRIM(:fullName) IS NULL OR LOWER(u.fullName) like LOWER(CONCAT('%',TRIM(:fullName), '%'))) " +
            "AND (TRIM(:phone) IS NULL OR LOWER(u.phone) like LOWER(CONCAT('%',TRIM(:phone), '%'))) " +
            "AND ( u.statusOnline in (:statusOnline)) " +
            "AND (:scoreFrom IS NULL OR re.score >= :scoreFrom) " +
            "AND (:scoreTo IS NULL OR re.score <= :scoreTo) " +
            "AND (:dateFrom IS NULL OR u.createdDate >= :dateFrom) " +
            "AND (:dateTo IS NULL OR u.createdDate <= :dateTo) "
    )
    Page<UserInfoDTO> doSearchDoctor(
            @Param("username") String username,
            @Param("email") String email,
            @Param("fullName") String fullName,
            @Param("phone") String phone,
            @Param("statusOnline") List<Integer> statusOnline,
            @Param("scoreFrom") Integer scoreFrom,
            @Param("scoreTo") Integer scoreTo,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("id1") Integer id1,
            Pageable paging);

    @Query("SELECT u FROM UserInfo u WHERE ?1 = -1 OR (SELECT r FROM Roles r WHERE r.id = ?1) member of u.role")
    List<UserInfo> findByRoleId(Integer id);

    @Query(value = "Select u from UserInfo u where u.username = ?1")
    public Optional<UserInfo> findByUsername(String userName);

    @EntityGraph(attributePaths = {UserInfo.Fields.email})
    Optional<UserInfo> findByEmail(String email);

    @Query(value = "SELECT new com.neo.core.dto.UserInfoDTO(u.id, u.username, u.password, u.fullName, u.dob, u.gender, " +
            "u.roles, ro.nameRole, u.phone,u.email, u.address, u.createdDate, u.statusOnline,u.idPeerjs,u.idDoctor,u.status, re.score, u1.fullName) " +
            "FROM UserInfo u " +
            "left join Roles ro on u.roles = ro.idRole " +
            "left join results re on (re.userId = u.id  and re.numberTest  = (SELECT MAX(re1.numberTest) FROM results re1 where re.userId = re1.userId  ) )" +
            "left join UserInfo u1 on u1.id = u.idDoctor " +
            " where u.id = ?1 " +
            "group by u.id, u.username, u.password, u.fullName," +
            " u.dob, u.gender, u.roles, ro.nameRole, u.phone,u.email, u.address, " +
            "u.createdDate, u.statusOnline,u.idPeerjs,u.idDoctor,u.status, re.score, u1.fullName")
    Optional<UserInfoDTO> findByIdUser(Integer id);


    @Query("SELECT u.roles FROM UserInfo u WHERE u.id = ?1 ")
    Integer  getRoles(Integer id);

    @Query("SELECT MAX(r.numberTest) FROM results r WHERE r.userId = ?1 ")
    Integer  getNumberTest(Integer id);


    @Query(value = "SELECT new com.neo.core.dto.UserInfoDTO(u.id, u.username, u.password, u.fullName, u.dob, u.gender, " +
            "u.roles, ro.nameRole, u.phone,u.email, u.address, u.createdDate, u.statusOnline,u.idPeerjs,u.idDoctor,u.status, re.score, u1.fullName) " +
            "FROM UserInfo u " +
            "left join Roles ro on u.roles = ro.idRole " +
            "left join results re on (re.userId = u.id  and re.numberTest  = (SELECT MAX(re1.numberTest) FROM results re1 where re.userId = re1.userId  ) )" +
            "left join UserInfo u1 on u1.id = u.idDoctor " +
            " where u.roles = ?1 " )
    List<UserInfoDTO>  FindIdDoctor(Integer id);

}
