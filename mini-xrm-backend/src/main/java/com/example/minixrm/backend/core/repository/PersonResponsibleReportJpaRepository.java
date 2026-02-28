package com.example.minixrm.backend.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.minixrm.backend.core.domain.entity.PersonResponsibleReportItem;

public interface PersonResponsibleReportJpaRepository extends JpaRepository<PersonResponsibleReportItem, Void> {
	
	Page<PersonResponsibleReportItem> findAll(Pageable pageable);

}
