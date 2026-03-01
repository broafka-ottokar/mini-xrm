package com.example.minixrm.backend.core.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.PersonResponsibleReportSortField;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportSortFieldDto;

@Component
public class PersonResponsibleReportSortFieldDtoMapper {
	
	public PersonResponsibleReportSortFieldDto toDto(PersonResponsibleReportSortField personResponsibleReportSortField) {
		if (personResponsibleReportSortField == null) {
			return null;
		}
		return PersonResponsibleReportSortFieldDto.valueOf(personResponsibleReportSortField.name());
	}
	
	public PersonResponsibleReportSortField fromDto(PersonResponsibleReportSortFieldDto personResponsibleReportSortFieldDto) {
		if (personResponsibleReportSortFieldDto == null) {
			return null;
		}
		return PersonResponsibleReportSortField.valueOf(personResponsibleReportSortFieldDto.name());
	}

}
