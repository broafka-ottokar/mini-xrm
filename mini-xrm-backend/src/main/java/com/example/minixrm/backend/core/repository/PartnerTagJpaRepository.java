package com.example.minixrm.backend.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.minixrm.backend.core.domain.entity.PartnerTag;

@Repository
public interface PartnerTagJpaRepository extends JpaRepository<PartnerTag, Long> {
	
	

}
