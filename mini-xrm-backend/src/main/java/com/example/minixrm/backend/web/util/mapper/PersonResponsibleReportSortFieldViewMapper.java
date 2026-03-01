package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportSortFieldDto;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportSortFieldView;

@Component
public class PersonResponsibleReportSortFieldViewMapper {

	public PersonResponsibleReportSortFieldDto fromView(PersonResponsibleReportSortFieldView view) {
		if (view == null) {
			return null;
		}
		return PersonResponsibleReportSortFieldDto.valueOf(view.name());
	}
	
}
