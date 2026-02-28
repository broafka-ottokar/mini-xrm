package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PartnersDto {

	private final List<PartnerDto> partners;
	
	public PartnersDto(List<PartnerDto> partners) {
		this.partners = partners;
	}
	
	public List<PartnerDto> getPartners() {
		return partners;
	}
	
}
