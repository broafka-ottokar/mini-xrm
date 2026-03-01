package com.example.minixrm.backend.core.domain;

public enum PersonResponsibleReportSortField {

	PERSON_RESPONSIBLE    ("personResponsible"),
	PARTNER_COUNT         ("partnerCount"),
	TOTAL_DURATION_MINUTES("totalDurationMinutes")
	;
	
	private final String fieldName;
	
	PersonResponsibleReportSortField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}

}
