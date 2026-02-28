package com.example.minixrm.backend.core.facade.dto;

public class ActivityDto {

	private final long id;
	private final String subject;
	private final String type;
	private final String description;
	private final int durationMinutes;
	private final String personResponsible;
	private final PartnerDto partner;
	
	public ActivityDto(
			long id,
			String subject,
			String type,
			String description,
			int durationMinutes,
			String personResponsible,
			PartnerDto partner
	) {
		this.id = id;
		this.subject = subject;
		this.type = type;
		this.description = description;
		this.durationMinutes = durationMinutes;
		this.personResponsible = personResponsible;
		this.partner = partner;
	}
	
	public long getId() {
		return id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getDurationMinutes() {
		return durationMinutes;
	}
	
	public String getPersonResponsible() {
		return personResponsible;
	}
	
	public PartnerDto getPartner() {
		return partner;
	}
	
}
