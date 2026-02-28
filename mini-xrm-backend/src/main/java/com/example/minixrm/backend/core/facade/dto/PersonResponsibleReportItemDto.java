package com.example.minixrm.backend.core.facade.dto;

public class PersonResponsibleReportItemDto {
	
	private final String personResponsible;
	private final int partnerCount;
	private final int totalDurationMinutes;
	
	public PersonResponsibleReportItemDto(
			String personResponsible,
			int partnerCount,
			int totalDurationMinutes
	) {
		this.personResponsible = personResponsible;
		this.partnerCount = partnerCount;
		this.totalDurationMinutes = totalDurationMinutes;
	}
	
	public String getPersonResponsible() {
		return personResponsible;
	}
	
	public int getPartnerCount() {
		return partnerCount;
	}
	
	public int getTotalDurationMinutes() {
		return totalDurationMinutes;
	}

}
