package com.example.minixrm.backend.core.facade.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateOrUpdateActivityDto {

	private final String subject;
	private final String type;
	private final String description;
	
	@Min(1)
	private final int durationMinutes;
	
	@Size(min = 1, max = 150)
	private final String personResponsible;
	
	@NotNull
	private final long partnerId;
	
	public CreateOrUpdateActivityDto(
			String subject,
			String type,
			String description,
			int durationMinutes,
			String personResponsible,
			long partnerId
	) {
		this.subject = subject;
		this.type = type;
		this.description = description;
		this.durationMinutes = durationMinutes;
		this.personResponsible = personResponsible;
		this.partnerId = partnerId;
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
	
	public long getPartnerId() {
		return partnerId;
	}
	
}
