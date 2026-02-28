package com.example.minixrm.backend.web.facade;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.PartnerDtoFacade;
import com.example.minixrm.backend.core.facade.dto.PartnerPageDto;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerView;
import com.example.minixrm.backend.web.util.mapper.PartnerViewMapper;

@Component
public class PartnerViewFacade {
	
	private PartnerDtoFacade delegate;
	private PartnerViewMapper mapper;
	
	public PartnerViewFacade(
			PartnerDtoFacade delegate,
			PartnerViewMapper mapper
	) {
		this.delegate = delegate;
		this.mapper = mapper;
	}

	public PartnerPageView searchPartners(int page, int pageSize, Long partnerTagId) {
		PartnerPageDto all = delegate.searchPartners(page, pageSize, partnerTagId);
		return mapper.toView(all);
	}

	public void deletePartner(Long partnerId) {
		delegate.deletePartner(partnerId);
	}

	public Optional<PartnerView> getPartnerById(long partnerId) {
		return delegate
				.getPartnerById(partnerId)
				.map(mapper::toView);
	}

	public void createOrUpdate(CreateOrUpdatePartnerRequestView dto, Long partnerId) {
		delegate.createOrUpdate(mapper.toDto(dto), partnerId);
	}

}
