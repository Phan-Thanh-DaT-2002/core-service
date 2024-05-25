package com.neo.core.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.awt.*;
import java.util.Set;

@Entity
@Table(name = "ROLES")
@FieldNameConstants
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Roles extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "ID_ROLE")
    private Long idRole;

    @Column(name = "NAME_ROLE")
    private String nameRole;
    @JsonIgnore
    @ManyToMany(mappedBy = "role")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserInfo> userInfos;
}
