package com.example.minixrm.backend.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_responsible_report")
public class PersonResponsibleReportItem {
	
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "person_responsible", nullable = false)
	private String personResponsible;
	
	@Column(name = "partner_count", nullable = false)
	private int partnerCount;
	
	@Column(name = "total_duration_minutes", nullable = false)
	private int totalDurationMinutes;
	
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
