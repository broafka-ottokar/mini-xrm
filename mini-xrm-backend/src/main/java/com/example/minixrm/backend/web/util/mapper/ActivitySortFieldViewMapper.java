package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.ActivitySortFieldDto;
import com.example.minixrm.backend.web.openapi.v1.model.ActivitySortFieldView;

@Component
public class ActivitySortFieldViewMapper {
	
	public ActivitySortFieldDto fromView(ActivitySortFieldView activitySortFieldView) {
		if (activitySortFieldView == null) {
			return null;
		}
		return ActivitySortFieldDto.valueOf(activitySortFieldView.name());
	}

}
