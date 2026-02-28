package com.example.minixrm.backend.core.facade;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.facade.dto.PartnerTagsDto;
import com.example.minixrm.backend.core.service.PartnerTagService;
import com.example.minixrm.backend.core.util.mapper.PartnerTagDtoMapper;

@Component
@Validated
public class PartnerTagDtoFacade {
	
	private final PartnerTagService partnerTagService;
	private final PartnerTagDtoMapper partnerTagDtoMapper;
	
	public PartnerTagDtoFacade(
			PartnerTagService partnerTagService,
			PartnerTagDtoMapper partnerTagDtoMapper
	) {
		this.partnerTagService = partnerTagService;
		this.partnerTagDtoMapper = partnerTagDtoMapper;
	}

	public PartnerTagsDto findAll() {
		return partnerTagDtoMapper.toPartnerTagsDto(partnerTagService.findAll());
	}
	
}