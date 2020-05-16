package com.xisra.arch.reposerver.model;

import com.xisra.arch.reposerver.constant.DatabaseConstant;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = DatabaseConstant.SITE_SCHEMA)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
}