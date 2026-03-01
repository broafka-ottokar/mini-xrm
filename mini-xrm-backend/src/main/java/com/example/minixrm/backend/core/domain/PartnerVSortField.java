package com.example.minixrm.backend.core.domain;

public enum PartnerVSortField {
	
	NAME        ("name"),
	TAX_NUMBER  ("taxNumber"),
	HEADQUARTERS("headquarters"),
	STATUS      ("status"),
	;
	
	private final String fieldName;
	
	PartnerVSortField(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public String getFieldName() {
		return fieldName;
	}

}
