package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PartnerVDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVPageDto;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVView;

@Component
public class PartnerVViewMapper {
	
	private final PartnerTagViewMapper partnerTagViewMapper;
	
	public PartnerVViewMapper(
			PartnerTagViewMapper partnerTagViewMapper
	) {
		this.partnerTagViewMapper = partnerTagViewMapper;
	}
	
	public PartnerVView toView(PartnerVDto dto) {
		if (dto == null) {
			return null;
		}
		return new PartnerVView(
				dto.getId(),
				dto.getName(),
				dto.getTaxNumber(),
				dto.getHeadquarters(),
				dto.getStatus(),
				partnerTagViewMapper.toViewList(dto.getPartnerTags())
		);
	}

	public PartnerVPageView toView(PartnerVPageDto all) {
		if (all == null) {
			return null;
		}
		return new PartnerVPageView(
				all.getTotalElements(),
				all.getTotalPages(),
				all.getCurrentPage(),
				all.getPageSize(),
				all.getContent().stream().map(this::toView).toList()
		);
	}

}
