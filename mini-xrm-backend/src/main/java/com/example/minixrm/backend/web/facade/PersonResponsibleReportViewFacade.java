package com.example.minixrm.backend.web.facade;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.PersonResponsibleReportDtoFacade;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportPageView;
import com.example.minixrm.backend.web.util.mapper.PersonResponsibleReportViewMapper;

@Component
public class PersonResponsibleReportViewFacade {
	
	private final PersonResponsibleReportDtoFacade delegate;
	private final PersonResponsibleReportViewMapper mapper;
	
	public PersonResponsibleReportViewFacade(
			PersonResponsibleReportDtoFacade delegate,
			PersonResponsibleReportViewMapper mapper
	) {
		this.delegate = delegate;
		this.mapper = mapper;
	}
	
	public PersonResponsibleReportPageView getPersonResponsibleReport(int page, int pageSize) {
		PersonResponsibleReportPageDto reportPage = delegate.getPersonResponsibleReport(page, pageSize);
		return mapper.toView(reportPage);
	}

}
