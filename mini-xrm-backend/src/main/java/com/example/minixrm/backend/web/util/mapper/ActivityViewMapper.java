package com.example.minixrm.backend.web.util.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.ActivityDto;
import com.example.minixrm.backend.core.facade.dto.ActivityPageDto;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdateActivityDto;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityPageView;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityView;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdateActivityRequestView;

import jakarta.validation.Valid;

@Component
public class ActivityViewMapper {
	
	public ActivityView toView(ActivityDto dto) {
		if (dto == null) {
			return null;
		}
		return new ActivityView()
				.id(dto.getId())
				.subject(dto.getSubject())
				.type(dto.getType())
				.description(dto.getDescription())
				.durationMinutes((int) dto.getDurationMinutes())
				.personResponsible(dto.getPersonResponsible());
	}

	public ActivityPageView toView(ActivityPageDto dto) {
		if (dto == null) {
			return null;
		}
		ActivityPageView view = new ActivityPageView();
		view.setTotalElements(dto.getTotalElements());
		view.setTotalPages(dto.getTotalPages());
		view.setCurrentPage(dto.getCurrentPage());
		view.setPageSize(dto.getPageSize());
		List<ActivityView> content = dto.getContent().stream()
				.map(this::toView)
				.toList();
		view.setContent(content);
		return view;
	}

	public CreateOrUpdateActivityDto fromView(@Valid CreateOrUpdateActivityRequestView view) {
		if (view == null) {
			return null;
		}
		return new CreateOrUpdateActivityDto(
				view.getSubject(),
				view.getType(),
				view.getDescription(),
				view.getDurationMinutes(),
				view.getPersonResponsible(),
				view.getPartnerId()
		);
	}

}
