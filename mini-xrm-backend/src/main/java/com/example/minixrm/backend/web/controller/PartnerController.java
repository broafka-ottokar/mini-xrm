package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.web.facade.PartnerViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.PartnerApi;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVSortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Controller
@Validated
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
	public ResponseEntity<PartnerView> createPartner(@Valid CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView) {
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
	public ResponseEntity<PartnerView> updatePartner(
			@NotNull @Min(1) @Max(9223372036854775807L) Long partnerId,
			@Valid CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView
	) {
		PartnerView partnerView = facade.createOrUpdate(createOrUpdatePartnerRequestView, partnerId);
		return ResponseEntity.ok(partnerView);
	}

	@Override
	public ResponseEntity<PartnerVPageView> searchPartners(
	        @NotNull @Min(value = 0)  @Valid @RequestParam(value = "page", required = true) Integer page,
	        @NotNull @Min(value = 1)  @Valid @RequestParam(value = "pageSize", required = true) Integer pageSize,
	        @Min(value = 1L) @Max(value = 9223372036854775807L)  @Valid @RequestParam(value = "partnerTagId", required = false) @Nullable Long partnerTagId,
	        @Valid @RequestParam(value = "sortField", required = false) @Nullable PartnerVSortFieldView sortField,
	        @Valid @RequestParam(value = "sortDirection", required = false) @Nullable SortDirectionView sortDirection
	) {
		PartnerVPageView view = facade.searchPartners(page, pageSize, sortField, sortDirection, partnerTagId);
		return ResponseEntity.ok(view);
	}

}
