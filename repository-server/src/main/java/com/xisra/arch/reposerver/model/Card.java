package com.xisra.arch.reposerver.model;

import com.xisra.arch.reposerver.constant.DatabaseConstant;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = DatabaseConstant.CORE_SCHEMA)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @Column(name = "expire_at")
    private Date expireAt;
}