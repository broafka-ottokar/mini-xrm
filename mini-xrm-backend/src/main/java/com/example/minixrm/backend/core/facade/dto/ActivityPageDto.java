package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class ActivityPageDto extends AbstractPageDto<ActivityDto> {

	public ActivityPageDto(
			long totalElements,
			int totalPages,
			int currentPage,
			int pageSize,
			List<ActivityDto> activities
	) {
		super(totalElements, totalPages, currentPage, pageSize, activities);
	}
	
}
