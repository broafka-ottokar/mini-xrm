package com.example.minixrm.backend.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;

public interface PartnerJpaRepository extends JpaRepository<Partner, Long> {

	Page<Partner> findByPartnerTags(Pageable pageable, PartnerTag partnerTag);

}
