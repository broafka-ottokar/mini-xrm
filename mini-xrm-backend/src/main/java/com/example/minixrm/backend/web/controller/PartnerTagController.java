package com.example.minixrm.backend.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.minixrm.backend.web.facade.PartnerTagViewFacade;
import com.example.minixrm.backend.web.openapi.v1.api.PartnerTagApi;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerTagsView;

@Controller
public class PartnerTagController implements PartnerTagApi {

	private final PartnerTagViewFacade facade;

	public PartnerTagController(
			PartnerTagViewFacade facade
	) {
		this.facade = facade;
	}

	@Override
	@RequestMapping(
		method = RequestMethod.GET,
		value = PartnerTagApi.PATH_LIST_PARTNER_TAGS,
		produces = { "application/json" }
	)
	public ResponseEntity<PartnerTagsView> listPartnerTags() {
		PartnerTagsView all = facade.findAll();
		return ResponseEntity.ok(all);
	}

}
