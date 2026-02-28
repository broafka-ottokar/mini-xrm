package com.example.minixrm.backend.core.util.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.facade.dto.PartnerTagDto;
import com.example.minixrm.backend.core.facade.dto.PartnerTagsDto;

@Component
public class PartnerTagDtoMapper {
	
	public PartnerTagDto toDto(PartnerTag entity) {
		if (entity == null) {
			return null;
		}
		return new PartnerTagDto(
				entity.getId(),
				entity.getName()
		);
	}

	public List<PartnerTagDto> toDtoList(List<PartnerTag> entities) {
		if (entities == null) {
			return null;
		}
		return entities.stream()
				.map(this::toDto)
				.toList();
	}
	
	public PartnerTagsDto toPartnerTagsDto(List<PartnerTag> entities) {
		if (entities == null) {
			return null;
		}
		return new PartnerTagsDto(
				toDtoList(entities)
		);
	}

}
