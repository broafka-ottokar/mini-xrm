package com.example.minixrm.backend.core.domain;

public enum ActivitySortField {

	SUBJECT           ("subject"),
	TYPE		      ("type"),
	DESCRIPTION       ("description"),
	DURATION_MINUTES  ("durationMinutes"),
	PERSON_RESPONSIBLE("personResponsible"),
	PARTNER_NAME      ("partner.name"),
	;
	
	private final String fieldName;
	
	ActivitySortField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}

}
