package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerDto;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerView;

@Component
public class PartnerViewMapper {
	
	private final PartnerStatusViewMapper partnerStatusViewMapper;
	private final PartnerTagViewMapper partnerTagViewMapper;
	
	public PartnerViewMapper(
			PartnerStatusViewMapper partnerStatusViewMapper,
			PartnerTagViewMapper partnerTagViewMapper
	) {
		this.partnerStatusViewMapper = partnerStatusViewMapper;
		this.partnerTagViewMapper = partnerTagViewMapper;
	}
	
	public PartnerView toView(PartnerDto dto) {
		if (dto == null) {
			return null;
		}
		return new PartnerView(
				dto.getId(),
				dto.getName(),
				dto.getTaxNumber(),
				dto.getHeadquarters(),
				partnerStatusViewMapper.toView(dto.getStatus()),
				partnerTagViewMapper.toViewList(dto.getPartnerTags())
		);
	}

	public CreateOrUpdatePartnerDto toDto(CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView) {
		if (createOrUpdatePartnerRequestView == null) {
			return null;
		}
		return new CreateOrUpdatePartnerDto(
				createOrUpdatePartnerRequestView.getName(),
				createOrUpdatePartnerRequestView.getTaxNumber(),
				createOrUpdatePartnerRequestView.getHeadquarters(),
				partnerStatusViewMapper.fromView(createOrUpdatePartnerRequestView.getStatus()),
				createOrUpdatePartnerRequestView.getTagIds()
		);
		
	}

}
