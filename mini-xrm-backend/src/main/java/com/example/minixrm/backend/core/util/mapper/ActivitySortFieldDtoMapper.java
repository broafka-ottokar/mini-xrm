package com.example.minixrm.backend.core.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.ActivitySortField;
import com.example.minixrm.backend.core.facade.dto.ActivitySortFieldDto;

@Component
public class ActivitySortFieldDtoMapper {
	
	public ActivitySortFieldDto toDto(ActivitySortField activitySortField) {
		if (activitySortField == null) {
			return null;
		}
		return ActivitySortFieldDto.valueOf(activitySortField.name());
	}
	
	public ActivitySortField fromDto(ActivitySortFieldDto activitySortFieldDto) {
		if (activitySortFieldDto == null) {
			return null;
		}
		return ActivitySortField.valueOf(activitySortFieldDto.name());
	}

}
