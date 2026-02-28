package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.web.facade.PartnerViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.PartnerApi;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Controller
public class PartnerController implements PartnerApi {

	private final PartnerViewFacade facade;

	public PartnerController(
			PartnerViewFacade facade
	) {
		this.facade = facade;
	}

	@Override
	@RequestMapping(
		method = RequestMethod.POST,
		value = PartnerApi.PATH_CREATE_PARTNER,
		produces = { "application/json" },
		consumes = { "application/json" }
	)
	public ResponseEntity<Void> createPartner(@Valid CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView) {
		return this.updatePartner(null, createOrUpdatePartnerRequestView);
	}

	@Override
	@RequestMapping(
		method = RequestMethod.DELETE,
		value = PartnerApi.PATH_DELETE_PARTNER,
		produces = { "application/json" }
	)
	public ResponseEntity<Void> deletePartner(@NotNull @Min(1) @Max(9223372036854775807L) Long partnerId) {
		facade.deletePartner(partnerId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = PartnerApi.PATH_LOAD_PARTNER,
		produces = { "application/json" }
	)
	public ResponseEntity<PartnerView> loadPartner(@NotNull @Min(1) @Max(9223372036854775807L) Long partnerId) {
		return facade.getPartnerById(partnerId)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> ApplicationException.entityNotFound(partnerId));
	}

	@Override
	@RequestMapping(
		method = RequestMethod.PUT,
		value = PartnerApi.PATH_UPDATE_PARTNER,
		produces = { "application/json" },
		consumes = { "application/json" }
	)
	public ResponseEntity<Void> updatePartner(
			@NotNull @Min(1) @Max(9223372036854775807L) Long partnerId,
			@Valid CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView
	) {
		facade.createOrUpdate(createOrUpdatePartnerRequestView, partnerId);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<PartnerPageView> searchPartners(
			@NotNull @Min(0) @Valid Integer page,
			@NotNull @Min(1) @Valid Integer pageSize,
			@Min(1) @Max(9223372036854775807L) @Valid Long partnerTagId
	) {
		PartnerPageView view = facade.searchPartners(page, pageSize, partnerTagId);
		return ResponseEntity.ok(view);
	}

}
