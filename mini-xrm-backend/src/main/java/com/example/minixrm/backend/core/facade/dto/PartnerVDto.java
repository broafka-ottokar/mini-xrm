package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public class PartnerVDto {
	
	private final long id;
	private final String name;
	private final String taxNumber;
	private final String headquarters;
	private final String status;
	private final List<PartnerTagDto> partnerTags;
	
	public PartnerVDto(
			long id,
			String name,
			String taxNumber,
			String headquarters,
			String status,
			List<PartnerTagDto> partnerTags
	) {
		this.id = id;
		this.name = name;
		this.taxNumber = taxNumber;
		this.headquarters = headquarters;
		this.status = status;
		this.partnerTags = partnerTags;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getTaxNumber() {
		return taxNumber;
	}
	
	public String getHeadquarters() {
		return headquarters;
	}
	
	public String getStatus() {
		return status;
	}
	
	public List<PartnerTagDto> getPartnerTags() {
		return partnerTags;
	}

}
