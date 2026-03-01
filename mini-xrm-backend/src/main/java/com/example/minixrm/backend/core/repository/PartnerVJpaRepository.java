package com.example.minixrm.backend.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.domain.entity.PartnerV;

public interface PartnerVJpaRepository extends JpaRepository<PartnerV, Long> {

	Page<PartnerV> findByPartnerTags(Pageable pageable, PartnerTag partnerTag);

}
