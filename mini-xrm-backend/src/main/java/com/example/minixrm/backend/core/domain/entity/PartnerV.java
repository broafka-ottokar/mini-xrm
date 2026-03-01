package com.example.minixrm.backend.core.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "partner_v")
public class PartnerV {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 150)
	@NotNull
	@Size(max = 150)
	private String name;
	
	@Column(name = "tax_number", nullable = true, length = 20)
	@NotNull
	@Size(max = 20)
	private String taxNumber;
	
	@Column(name = "headquarters", nullable = true, length = 150)
	@NotNull
	@Size(max = 150)
	private String headquarters;

	@Column(name = "status", nullable = false, length = 50)
	@NotNull
	@Size(max = 50)
	private String status;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "partner_x_partner_tag",
			joinColumns = @JoinColumn(name = "partner_id"),
			inverseJoinColumns = @JoinColumn(name = "partner_tag_id")
	)
	private List<PartnerTag> partnerTags = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTaxNumber() {
		return taxNumber;
	}
	
	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}
	
	public String getHeadquarters() {
		return headquarters;
	}
	
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<PartnerTag> getPartnerTags() {
		return partnerTags;
	}
	
	public void setPartnerTags(List<PartnerTag> partnerTags) {
		this.partnerTags = partnerTags;
	}

}