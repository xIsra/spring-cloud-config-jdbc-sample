package com.xisra.arch.reposerver.repository;

import com.xisra.arch.reposerver.lib.scanner.SiteRepositoryScanFilter;
import com.xisra.arch.reposerver.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

@SiteRepositoryScanFilter
public interface MemberRepository extends JpaRepository<Member, Long> {
}