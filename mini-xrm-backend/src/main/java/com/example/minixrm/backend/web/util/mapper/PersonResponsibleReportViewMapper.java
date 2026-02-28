package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportItemDto;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportItemView;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportPageView;

@Component
public class PersonResponsibleReportViewMapper {
	
	public PersonResponsibleReportPageView toView(PersonResponsibleReportPageDto dto) {
		if (dto == null) {
			return null;
		}
		return new PersonResponsibleReportPageView(
				dto.getTotalElements(),
				dto.getTotalPages(),
				dto.getCurrentPage(),
				dto.getPageSize(),
				dto.getContent().stream().map(this::toView).toList()
		);
	}

	private PersonResponsibleReportItemView toView(PersonResponsibleReportItemDto personresponsiblereportitemdto1) {
		if (personresponsiblereportitemdto1 == null) {
			return null;
		}
		return new PersonResponsibleReportItemView(
				personresponsiblereportitemdto1.getPersonResponsible(),
				personresponsiblereportitemdto1.getTotalDurationMinutes(),
				personresponsiblereportitemdto1.getPartnerCount()
		);
	}

}
