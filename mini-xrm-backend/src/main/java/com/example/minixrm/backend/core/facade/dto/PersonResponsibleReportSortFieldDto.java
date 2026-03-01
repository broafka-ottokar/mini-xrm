package com.example.minixrm.backend.core.facade.dto;

public enum PersonResponsibleReportSortFieldDto {

	PERSON_RESPONSIBLE    ("personResponsible"),
	PARTNER_COUNT         ("partnerCount"),
	TOTAL_DURATION_MINUTES("totalDurationMinutes")
	;
	
	private final String fieldName;
	
	PersonResponsibleReportSortFieldDto(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}

}
