package com.example.minixrm.backend.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.minixrm.backend.core.domain.PersonResponsibleReportSortField;
import com.example.minixrm.backend.core.domain.SortDirection;
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
	
	public Page<PersonResponsibleReportItem> getPersonResponsibleReport(int page, int pageSize, PersonResponsibleReportSortField sortField, SortDirection sortDirection) {
		Pageable pageable;
		if ((sortField != null) && (sortDirection != null)) {
			Direction direction = sortDirection.toDirection();
			pageable = PageRequest.of(page, pageSize, direction, sortField.getFieldName());
		} else {
			pageable = PageRequest.of(page, pageSize);
		}
		Page<PersonResponsibleReportItem> reportPage = repository.findAll(pageable);
		return reportPage;
	}

}
