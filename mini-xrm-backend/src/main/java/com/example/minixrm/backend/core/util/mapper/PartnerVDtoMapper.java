package com.example.minixrm.backend.core.util.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.PartnerV;
import com.example.minixrm.backend.core.facade.dto.PartnerVPageDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVDto;

@Component
public class PartnerVDtoMapper {

	private final PartnerTagDtoMapper partnerTagDtoMapper;
	
	public PartnerVDtoMapper(
			PartnerTagDtoMapper partnerTagDtoMapper
	) {
		this.partnerTagDtoMapper = partnerTagDtoMapper;
	}
	
	public PartnerVDto toDto(PartnerV entity) {
		return new PartnerVDto(
				entity.getId(),
				entity.getName(),
				entity.getTaxNumber(),
				entity.getHeadquarters(),
				entity.getStatus(),
				partnerTagDtoMapper.toDtoList(entity.getPartnerTags())
		);
	}
	
	public PartnerVPageDto toDto(Page<PartnerV> all) {
		return new PartnerVPageDto(
				all.getTotalElements(),
				all.getTotalPages(),
				all.getPageable().getPageNumber(),
				all.getPageable().getPageSize(),
				all.getContent().stream().map(this::toDto).toList()
		);
	}

}
