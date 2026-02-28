package com.example.minixrm.backend.core.util.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.PersonResponsibleReportItem;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportItemDto;
import com.example.minixrm.backend.core.facade.dto.PersonResponsibleReportPageDto;

@Component
public class PersonResponsibleReportDtoMapper {
	
	public PersonResponsibleReportPageDto toDto(Page<PersonResponsibleReportItem> all) {
		return new PersonResponsibleReportPageDto(
				all.getTotalElements(),
				all.getTotalPages(),
				all.getPageable().getPageNumber(),
				all.getPageable().getPageSize(),
				all.getContent().stream().map(this::toDto).toList()
		);
	}

	private PersonResponsibleReportItemDto toDto(PersonResponsibleReportItem entity) {
		return new PersonResponsibleReportItemDto(
				entity.getPersonResponsible(),
				entity.getPartnerCount(),
				entity.getTotalDurationMinutes()
		);
	}

}
