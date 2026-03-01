package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;

@Component
public class SortDirectionViewMapper {
	
	public SortDirectionDto fromView(SortDirectionView sortDirection) {
		if (sortDirection == null) {
			return null;
		}
		return SortDirectionDto.valueOf(sortDirection.name());
	}

}
