package com.example.minixrm.backend.core.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.SortDirection;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;

@Component
public class SortDirectionDtoMapper {
	
	public SortDirectionDto toDto(SortDirection sortDirection) {
		if (sortDirection == null) {
			return null;
		}
		return SortDirectionDto.valueOf(sortDirection.name());
	}
	
	public SortDirection fromDto(SortDirectionDto sortDirectionDto) {
		if (sortDirectionDto == null) {
			return null;
		}
		return SortDirection.valueOf(sortDirectionDto.name());
	}

}
