package com.example.minixrm.backend.core.facade.dto;

public class PartnerTagDto {
	
	private final long id;
	private final String name;
	
	public PartnerTagDto(
			long id,
			String name
	) {
		this.id = id;
		this.name = name;
	}
	
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
