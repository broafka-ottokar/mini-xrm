package com.example.minixrm.backend.core.facade;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.minixrm.backend.core.domain.entity.PersonResponsibleReportItem;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;
import com.example.minixrm.backend.core.service.PersonResponsibleReportService;
import com.example.minixrm.backend.core.util.mapper.PersonResponsibleReportDtoMapper;

@Service
public class PersonResponsibleReportDtoFacade {
	
	private final PersonResponsibleReportService delegate;
	private final PersonResponsibleReportDtoMapper mapper;
	
	public PersonResponsibleReportDtoFacade(
			PersonResponsibleReportService deledate,
			PersonResponsibleReportDtoMapper mapper
	) {
		this.delegate = deledate;
		this.mapper = mapper;
	}
	
	public PersonResponsibleReportPageDto getPersonResponsibleReport(int page, int pageSize) {
		Page<PersonResponsibleReportItem> reportPage = delegate.getPersonResponsibleReport(page, pageSize);
		return mapper.toDto(reportPage);
	}

}
