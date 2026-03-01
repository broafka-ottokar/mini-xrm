package com.example.minixrm.backend.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;

public interface ActivityJpaRepository extends JpaRepository<Activity, Long> {
	
	Page<Activity> findByPartner(Partner partner, Pageable pageable);

}
