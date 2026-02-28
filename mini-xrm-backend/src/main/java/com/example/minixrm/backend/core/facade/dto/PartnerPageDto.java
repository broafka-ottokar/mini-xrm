package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PartnerPageDto extends AbstractPageDto<PartnerDto> {

	public PartnerPageDto(
			long totalElements,
			int totalPages,
			int currentPage,
			int pageSize,
			List<PartnerDto> partners
	) {
		super(totalElements, totalPages, currentPage, pageSize, partners);
	}
	
}
