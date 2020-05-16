package com.xisra.arch.reposerver.model;

import com.xisra.arch.reposerver.lib.scanner.CoreRepositoryScanFilter;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@CoreRepositoryScanFilter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int expirationMonth;
    private int expirationYear;
}