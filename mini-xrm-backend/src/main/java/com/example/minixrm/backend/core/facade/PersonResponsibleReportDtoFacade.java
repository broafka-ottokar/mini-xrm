package com.example.minixrm.backend.core.facade;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.minixrm.backend.core.domain.PersonResponsibleReportSortField;
import com.example.minixrm.backend.core.domain.SortDirection;
import com.example.minixrm.backend.core.domain.entity.PersonResponsibleReportItem;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportSortFieldDto;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.core.service.PersonResponsibleReportService;
import com.example.minixrm.backend.core.util.mapper.PersonResponsibleReportDtoMapper;
import com.example.minixrm.backend.core.util.mapper.PersonResponsibleReportSortFieldDtoMapper;
import com.example.minixrm.backend.core.util.mapper.SortDirectionDtoMapper;

@Service
public class PersonResponsibleReportDtoFacade {
	
	private final PersonResponsibleReportService delegate;
	private final PersonResponsibleReportDtoMapper personResponsibleReportDtoMapper;
	private final PersonResponsibleReportSortFieldDtoMapper personResponsibleReportSortFieldDtoMapper;
	private final SortDirectionDtoMapper sortDirectionDtoMapper;
	
	public PersonResponsibleReportDtoFacade(
			PersonResponsibleReportService deledate,
			PersonResponsibleReportDtoMapper personResponsibleReportDtoMapper,
			PersonResponsibleReportSortFieldDtoMapper personResponsibleReportSortFieldDtoMapper,
			SortDirectionDtoMapper sortDirectionDtoMapper
	) {
		this.delegate = deledate;
		this.personResponsibleReportDtoMapper = personResponsibleReportDtoMapper;
		this.personResponsibleReportSortFieldDtoMapper = personResponsibleReportSortFieldDtoMapper;
		this.sortDirectionDtoMapper = sortDirectionDtoMapper;
	}
	
	public PersonResponsibleReportPageDto getPersonResponsibleReport(
			int page,
			int pageSize,
			PersonResponsibleReportSortFieldDto sortFieldDto,
			SortDirectionDto sortDirectionDto
	) {
		PersonResponsibleReportSortField sortField = personResponsibleReportSortFieldDtoMapper.fromDto(sortFieldDto);
		SortDirection sortDirection = sortDirectionDtoMapper.fromDto(sortDirectionDto);
		Page<PersonResponsibleReportItem> reportPage = delegate.getPersonResponsibleReport(page, pageSize, sortField, sortDirection);
		return personResponsibleReportDtoMapper.toDto(reportPage);
	}

}
