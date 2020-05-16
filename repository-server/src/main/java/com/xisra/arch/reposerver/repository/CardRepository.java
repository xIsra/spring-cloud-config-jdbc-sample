package com.xisra.arch.reposerver.repository;

import com.xisra.arch.reposerver.lib.scanner.CoreRepositoryScanFilter;
import com.xisra.arch.reposerver.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

@CoreRepositoryScanFilter
public interface CardRepository extends JpaRepository<Card, Long> {
}