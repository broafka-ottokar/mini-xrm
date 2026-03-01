package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minixrm.backend.web.facade.PersonResponsibleReportViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.ReportApi;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PersonResponsibleReportSortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Controller
@Validated
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
	        @NotNull @Min(value = 0)  @Valid @RequestParam(value = "page", required = true) Integer page,
	        @NotNull @Min(value = 1)  @Valid @RequestParam(value = "pageSize", required = true) Integer pageSize,
	        @Valid @RequestParam(value = "sortField", required = false) @Nullable PersonResponsibleReportSortFieldView sortField,
	        @Valid @RequestParam(value = "sortDirection", required = false) @Nullable SortDirectionView sortDirection
	) {
		PersonResponsibleReportPageView report = facade.getPersonResponsibleReport(page, pageSize, sortField, sortDirection);
		return ResponseEntity.ok(report);
		
	}

}
