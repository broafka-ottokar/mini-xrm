package com.example.minixrm.backend.core.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.PartnerStatus;
import com.example.minixrm.backend.core.facade.dto.PartnerStatusDto;

@Component
public class PartnerStatusDtoMapper {
	
	public PartnerStatusDto toDto(PartnerStatus partnerStatus) {
		if (partnerStatus == null) {
			return null;
		}
		switch (partnerStatus) {
			case ACTIVE: {
				return PartnerStatusDto.ACTIVE;
			}
			case INACTIVE: {
				return PartnerStatusDto.INACTIVE;
			}
			default: {
				throw new IllegalArgumentException("Unknown PartnerStatus: " + partnerStatus);
			}
		}
	}

	public PartnerStatus fromDto(PartnerStatusDto status) {
		if (status == null) {
			return null;
		}
		switch (status) {
			case ACTIVE: {
				return PartnerStatus.ACTIVE;
			}
			case INACTIVE: {
				return PartnerStatus.INACTIVE;
			}
			default: {
				throw new IllegalArgumentException("Unknown PartnerStatusDto: " + status);
			}
		}
	}

}
