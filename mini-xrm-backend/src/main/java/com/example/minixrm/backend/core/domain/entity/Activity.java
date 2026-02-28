package com.example.minixrm.backend.core.domain.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "activity")
@SQLDelete(sql = "UPDATE activity SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Activity {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Version
	@Column(name = "version")
	private Long version;

	@Column(name = "subject", nullable = false, length = 150)
	@NotNull
	@Size(max = 150)
	private String subject;
	
	@Column(name = "type", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	private String type;
	
	@Column(name = "description", nullable = false, length = 100000)
	@Size(max = 100000)
	private String description;
	
	@Column(name = "duration_minutes", nullable = false)
	@NotNull
	@Min(1)
	private Integer durationMinutes;
	
	@Column(name = "person_responsible", nullable = false, length = 150)
	@NotNull
	@Size(max = 150)
	private String personResponsible;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partner_id", nullable = false)
	private Partner partner;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getDurationMinutes() {
		return durationMinutes;
	}
	
	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}
	
	public String getPersonResponsible() {
		return personResponsible;
	}
	
	public void setPersonResponsible(String personResponsible) {
		this.personResponsible = personResponsible;
	}
	
	public Partner getPartner() {
		return partner;
	}
	
	public void setPartner(Partner partner) {
		this.partner = partner;
	}

}