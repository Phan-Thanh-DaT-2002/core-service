package com.neo.core.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
import lombok.*;
import lombok.experimental.FieldNameConstants;

@Entity
@Table(name = "user_infor")
@FieldNameConstants
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class UserInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "DOB")
    private LocalDateTime dob;

    @Column(name = "GENDER")
    private Integer gender;

    @Column(name = "ROLES")
    private Integer roles;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "STATUS_ONLINE")
    private Integer statusOnline;
    
    @Column(name = "ID_PEERJS")
    private String idPeerjs;

    @Column(name = "ID_DOCTOR")
    private Integer idDoctor;

    @Column(name = "STATUS")
    private Integer status;


    @ManyToMany
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", insertable = true, updatable = true),
            inverseJoinColumns = @JoinColumn(name = "role_id", insertable = true, updatable = true))
    @Expose
    Set<Roles> role;

}
