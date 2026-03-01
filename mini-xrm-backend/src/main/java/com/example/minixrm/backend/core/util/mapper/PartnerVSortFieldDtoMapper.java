package com.example.minixrm.backend.core.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.PartnerVSortField;
import com.example.minixrm.backend.core.facade.dto.PartnerVSortFieldDto;

@Component
public class PartnerVSortFieldDtoMapper {
	
	public PartnerVSortFieldDto toDto(PartnerVSortField partnerVSortField) {
		if (partnerVSortField == null) {
			return null;
		}
		return PartnerVSortFieldDto.valueOf(partnerVSortField.name());
	}
	
	public PartnerVSortField fromDto(PartnerVSortFieldDto partnerVSortFieldDto) {
		if (partnerVSortFieldDto == null) {
			return null;
		}
		return PartnerVSortField.valueOf(partnerVSortFieldDto.name());
	}

}
