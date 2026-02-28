package com.example.minixrm.backend.web.util.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PartnerTagDto;
import com.example.minixrm.backend.core.facade.dto.PartnerTagsDto;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerTagView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerTagsView;

import jakarta.validation.Valid;

@Component
public class PartnerTagViewMapper {
	
	public PartnerTagView toView(PartnerTagDto dto) {
		if (dto == null) {
			return null;
		}
		return new PartnerTagView()
				.id(dto.getId())
				.name(dto.getName());
	}

	public List<@Valid PartnerTagView> toViewList(List<PartnerTagDto> dtos) {
		if (dtos == null) {
			return null;
		}
		return dtos.stream()
				.map(this::toView)
				.toList();
	}

	public PartnerTagsView toPartnerTagsView(PartnerTagsDto all) {
		if (all == null) {
			return null;
		}
		return new PartnerTagsView(toViewList(all.getPartnerTags()));
	}
	
}
