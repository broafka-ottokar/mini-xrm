package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.minixrm.backend.web.facade.PersonResponsibleReportViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.ReportApi;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportPageView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Controller
public class PersonResponsibleReportController implements ReportApi {

	private final PersonResponsibleReportViewFacade facade;

	public PersonResponsibleReportController(
			PersonResponsibleReportViewFacade service
	) {
		this.facade = service;
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = ReportApi.PATH_REPORT_PERSON_RESPONSIBLE,
		produces = { "application/json" }
	)
	public ResponseEntity<PersonResponsibleReportPageView> reportPersonResponsible(
			@NotNull @Min(0) @Valid Integer page,
			@NotNull @Min(1) @Valid Integer pageSize
	) {
		PersonResponsibleReportPageView report = facade.getPersonResponsibleReport(page, pageSize);
		return ResponseEntity.ok(report);
		
	}

}
