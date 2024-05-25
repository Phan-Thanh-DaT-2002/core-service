package com.neo.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private  Integer id;
    private  String username;
    private  String password;
    private  String fullName;
    private LocalDateTime dob;
    private Integer gender;
    private  Integer roles;
    private  String nameRole;
    private  String phone;
    private  String email;
    private  String address;
    private LocalDateTime createdDate;
    private Integer statusOnline;
    private  String idPeerjs;
    private  Integer idDoctor;
    private Integer status;
    private Integer score;
    private  String NameIdDoctor;

}
