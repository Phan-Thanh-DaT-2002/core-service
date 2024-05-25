package com.neo.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_results")
@FieldNameConstants
@Data
@NoArgsConstructor
@AllArgsConstructor
public class results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "NUMBER_OF_TESTS")
    private Integer numberTest;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "TIMES")
    private Integer time;

    @Column(name = "DAY_TEST")
    private LocalDateTime dayTest;

    @Column(name = "NOTE")
    private String note;
}
