package com.example.minixrm.backend.core.util.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.facade.dto.ActivityDto;
import com.example.minixrm.backend.core.facade.dto.ActivityPageDto;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdateActivityDto;

@Component
public class ActivityDtoMapper {
	
	private final PartnerDtoMapper partnerDtoMapper;
	
	public ActivityDtoMapper(PartnerDtoMapper partnerDtoMapper) {
		this.partnerDtoMapper = partnerDtoMapper;
	}
	
	public ActivityDto toDto(Activity entity) {
		return new ActivityDto(
				entity.getId(),
				entity.getSubject(),
				entity.getType(),
				entity.getDescription(),
				entity.getDurationMinutes(),
				entity.getPersonResponsible(),
				partnerDtoMapper.toDto(entity.getPartner())
		);
	}

	public Activity fromDto(
			CreateOrUpdateActivityDto dto,
			Activity existing,
			Partner partner
	) {
		Activity entity = (existing == null) ? new Activity() : existing;
		entity.setSubject(dto.getSubject());
		entity.setType(dto.getType());
		entity.setDescription(dto.getDescription());
		entity.setDurationMinutes(dto.getDurationMinutes());
		entity.setPersonResponsible(dto.getPersonResponsible());
		entity.setPartner(partner);
		return entity;
	}

	public ActivityPageDto toDto(Page<Activity> all) {
		return new ActivityPageDto(
				all.getTotalElements(),
				all.getTotalPages(),
				all.getPageable().getPageNumber(),
				all.getPageable().getPageSize(),
				all.getContent().stream().map(this::toDto).toList()
		);
	}

}
