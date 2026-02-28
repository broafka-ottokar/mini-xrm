package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;

@Component
public class CreateOrUpdatePartnerViewMapper {
	
	private final PartnerStatusViewMapper partnerStatusViewMapper;

	public CreateOrUpdatePartnerViewMapper(
			PartnerStatusViewMapper partnerStatusViewMapper
	) {
		this.partnerStatusViewMapper = partnerStatusViewMapper;
	}
	
	public CreateOrUpdatePartnerDto fromView(CreateOrUpdatePartnerRequestView view) {
		if (view == null) {
			return null;
		}
		return new CreateOrUpdatePartnerDto(
				view.getName(),
				view.getTaxNumber(),
				view.getHeadquarters(),
				partnerStatusViewMapper.fromView(view.getStatus()),
				view.getTagIds()
		);
		
	}

}
