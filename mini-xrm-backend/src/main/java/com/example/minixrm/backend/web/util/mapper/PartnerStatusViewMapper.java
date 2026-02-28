package com.example.minixrm.backend.web.util.mapper;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.dto.PartnerStatusDto;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerStatusView;

@Component
public class PartnerStatusViewMapper {
	
	public PartnerStatusView toView(PartnerStatusDto dto) {
		if (dto == null) {
			return null;
		}
		switch (dto) {
			case ACTIVE: {
				return PartnerStatusView.ACTIVE;
			}
			case INACTIVE: {
				return PartnerStatusView.INACTIVE;
			}
			default: {
				throw new IllegalArgumentException("Unknown PartnerStatusDto: " + dto);
			}
		}
	}

	public PartnerStatusDto fromView(PartnerStatusView status) {
		if (status == null) {
			return null;
		}
		switch (status) {
			case ACTIVE: {
				return PartnerStatusDto.ACTIVE;
			}
			case INACTIVE: {
				return PartnerStatusDto.INACTIVE;
			}
			default: {
				throw new IllegalArgumentException("Unknown PartnerStatusView: " + status);
			}
		}
	}

}
