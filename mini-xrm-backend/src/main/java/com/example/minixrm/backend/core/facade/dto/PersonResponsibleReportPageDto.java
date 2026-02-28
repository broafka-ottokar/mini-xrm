package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PersonResponsibleReportPageDto extends AbstractPageDto<PersonResponsibleReportItemDto> {
	
	public PersonResponsibleReportPageDto(
			long totalElements,
			int totalPages,
			int currentPage,
			int pageSize,
			List<PersonResponsibleReportItemDto> items
	) {
		super(totalElements, totalPages, currentPage, pageSize, items);
	}
	
}
