package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PartnerTagsDto {
	
	private final List<PartnerTagDto> partnerTags;
	
	public PartnerTagsDto(List<PartnerTagDto> partnerTags) {
		this.partnerTags = partnerTags;
	}
	
	public List<PartnerTagDto> getPartnerTags() {
		return partnerTags;
	}

}
