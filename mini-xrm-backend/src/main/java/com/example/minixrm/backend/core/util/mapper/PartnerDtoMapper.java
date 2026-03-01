package com.example.minixrm.backend.core.util.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerDto;

@Component
public class PartnerDtoMapper {

	private final PartnerStatusDtoMapper partnerStatusDtoMapper;
	private final PartnerTagDtoMapper partnerTagDtoMapper;
	
	public PartnerDtoMapper(
			PartnerStatusDtoMapper partnerStatusDtoMapper,
			PartnerTagDtoMapper partnerTagDtoMapper
	) {
		this.partnerStatusDtoMapper = partnerStatusDtoMapper;
		this.partnerTagDtoMapper = partnerTagDtoMapper;
	}
	
	public PartnerDto toDto(Partner entity) {
		return new PartnerDto(
				entity.getId(),
				entity.getName(),
				entity.getTaxNumber(),
				entity.getHeadquarters(),
				partnerStatusDtoMapper.toDto(entity.getStatus()),
				partnerTagDtoMapper.toDtoList(entity.getPartnerTags())
		);
	}
	
	public Partner toEntity(CreateOrUpdatePartnerDto dto, Partner existing, List<PartnerTag> partnerTags) {
		Partner entity = (existing == null) ? new Partner() : existing;
		entity.setName(dto.getName());
		entity.setTaxNumber(dto.getTaxNumber());
		entity.setHeadquarters(dto.getHeadquarters());
		entity.setStatus(partnerStatusDtoMapper.fromDto(dto.getStatus()));
		entity.setPartnerTags(partnerTags);
		return entity;
	}

}
