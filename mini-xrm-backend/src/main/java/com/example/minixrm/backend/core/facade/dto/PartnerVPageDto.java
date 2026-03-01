package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PartnerVPageDto extends AbstractPageDto<PartnerVDto> {

	public PartnerVPageDto(
			long totalElements,
			int totalPages,
			int currentPage,
			int pageSize,
			List<PartnerVDto> partners
	) {
		super(totalElements, totalPages, currentPage, pageSize, partners);
	}
	
}
