package com.example.minixrm.backend.web.facade;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.PartnerTagDtoFacade;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerTagsView;
import com.example.minixrm.backend.web.util.mapper.PartnerTagViewMapper;

@Component
public class PartnerTagViewFacade {
	
	private PartnerTagDtoFacade delegate;
	private PartnerTagViewMapper mapper;
	
	public PartnerTagViewFacade(
			PartnerTagDtoFacade delegate,
			PartnerTagViewMapper mapper
	) {
		this.delegate = delegate;
		this.mapper = mapper;
	}

	public PartnerTagsView findAll() {
		return mapper.toPartnerTagsView(delegate.findAll());
	}

}
