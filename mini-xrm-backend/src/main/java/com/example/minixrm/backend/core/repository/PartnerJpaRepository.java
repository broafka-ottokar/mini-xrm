package com.example.minixrm.backend.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.Partner;

public interface PartnerJpaRepository extends JpaRepository<Partner, Long> {

}
