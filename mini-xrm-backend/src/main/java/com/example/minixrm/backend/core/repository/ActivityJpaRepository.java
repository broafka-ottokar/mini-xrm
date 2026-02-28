package com.example.minixrm.backend.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.Activity;

public interface ActivityJpaRepository extends JpaRepository<Activity, Long> {
	
	Page<Activity> findByPartnerId(Long partnerId, Pageable pageable);

}
