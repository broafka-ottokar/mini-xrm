package com.example.minixrm.backend.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.minixrm.backend.core.domain.entity.PersonResponsibleReportItem;
import com.example.minixrm.backend.core.repository.PersonResponsibleReportJpaRepository;

@Service
public class PersonResponsibleReportService {
	
	private final PersonResponsibleReportJpaRepository repository;
	
	public PersonResponsibleReportService(
			PersonResponsibleReportJpaRepository repository
	) {
		this.repository = repository;
	}
	
	public Page<PersonResponsibleReportItem> getPersonResponsibleReport(int page, int pageSize) {
		Pageable pageable = Pageable.ofSize(pageSize).withPage(page);
		Page<PersonResponsibleReportItem> reportPage = repository.findAll(pageable);
		return reportPage;
	}

}
