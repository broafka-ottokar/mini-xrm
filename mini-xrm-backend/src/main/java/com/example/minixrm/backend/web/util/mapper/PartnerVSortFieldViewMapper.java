package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PartnerVSortFieldDto;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVSortFieldView;

@Component
public class PartnerVSortFieldViewMapper {
	
	public PartnerVSortFieldDto fromView(PartnerVSortFieldView partnerVSortFieldView) {
		if (partnerVSortFieldView == null) {
			return null;
		}
		return PartnerVSortFieldDto.valueOf(partnerVSortFieldView.name());
	}

}
