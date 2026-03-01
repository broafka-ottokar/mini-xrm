package com.example.minixrm.backend.web.facade;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.PersonResponsibleReportDtoFacade;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportSortFieldDto;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportSortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;
import com.example.minixrm.backend.web.util.mapper.PersonResponsibleReportSortFieldViewMapper;
import com.example.minixrm.backend.web.util.mapper.PersonResponsibleReportViewMapper;
import com.example.minixrm.backend.web.util.mapper.SortDirectionViewMapper;

@Component
public class PersonResponsibleReportViewFacade {
	
	private final PersonResponsibleReportDtoFacade delegate;
	private final PersonResponsibleReportViewMapper personResponsibleReportViewMapper;
	private final PersonResponsibleReportSortFieldViewMapper personResponsibleReportSortFieldViewMapper;
	private final SortDirectionViewMapper sortDirectionViewMapper;
	
	public PersonResponsibleReportViewFacade(
			PersonResponsibleReportDtoFacade delegate,
			PersonResponsibleReportViewMapper personResponsibleReportViewMapper,
			PersonResponsibleReportSortFieldViewMapper personResponsibleReportSortFieldViewMapper,
			SortDirectionViewMapper sortDirectionViewMapper
	) {
		this.delegate = delegate;
		this.personResponsibleReportViewMapper = personResponsibleReportViewMapper;
		this.personResponsibleReportSortFieldViewMapper = personResponsibleReportSortFieldViewMapper;
		this.sortDirectionViewMapper = sortDirectionViewMapper;
	}
	
	public PersonResponsibleReportPageView getPersonResponsibleReport(
			int page,
			int pageSize,
			PersonResponsibleReportSortFieldView sortFieldView,
			SortDirectionView sortDirectionView
	) {
		PersonResponsibleReportSortFieldDto sortFieldDto = personResponsibleReportSortFieldViewMapper.fromView(sortFieldView);
		SortDirectionDto sortDirectionDto = sortDirectionViewMapper.fromView(sortDirectionView);
		PersonResponsibleReportPageDto reportPage = delegate.getPersonResponsibleReport(page, pageSize, sortFieldDto, sortDirectionDto);
		return personResponsibleReportViewMapper.toView(reportPage);
	}

}
