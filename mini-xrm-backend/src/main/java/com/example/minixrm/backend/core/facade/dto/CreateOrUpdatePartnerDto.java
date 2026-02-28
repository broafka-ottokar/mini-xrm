package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrUpdatePartnerDto {

	@NotNull
	@Size(min = 0, max = 150)
	private final String name;
	
	@NotNull
	@Size(min = 0, max = 20)
	private final String taxNumber;
	
	@NotNull
	@Size(min = 0, max = 150)
	private final String headquarters;
	
	@NotNull
	private final PartnerStatusDto status;
	
	@NotNull
	private final List<Long> partnerTagIds;
	
	public CreateOrUpdatePartnerDto(
			String name,
			String taxNumber,
			String headquarters,
			PartnerStatusDto status,
			List<Long> partnerTagIds
	) {
		this.name = name;
		this.taxNumber = taxNumber;
		this.headquarters = headquarters;
		this.status = status;
		this.partnerTagIds = partnerTagIds;
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

	public PartnerStatusDto getStatus() {
		return status;
	}
	
	public List<Long> getPartnerTagIds() {
		return partnerTagIds;
	}
	
}
