package com.example.minixrm.backend.web.facade;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.facade.PartnerDtoFacade;
import com.example.minixrm.backend.core.facade.dto.PartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVPageDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVSortFieldDto;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.web.openapi.v1.model.CreateOrUpdatePartnerRequestView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVPageView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVSortFieldView;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerView;
import com.example.minixrm.backend.web.openapi.v1.model.SortDirectionView;
import com.example.minixrm.backend.web.util.mapper.PartnerVSortFieldViewMapper;
import com.example.minixrm.backend.web.util.mapper.PartnerVViewMapper;
import com.example.minixrm.backend.web.util.mapper.PartnerViewMapper;
import com.example.minixrm.backend.web.util.mapper.SortDirectionViewMapper;

@Component
public class PartnerViewFacade {
	
	private PartnerDtoFacade delegate;
	private PartnerViewMapper partnerViewMapper;
	private PartnerVViewMapper partnerVViewMapper;
	private SortDirectionViewMapper sortDirectionViewMapper;
	private PartnerVSortFieldViewMapper partnerVSortFieldViewMapper;
	
	public PartnerViewFacade(
			PartnerDtoFacade delegate,
			PartnerViewMapper partnerViewMapper,
			PartnerVViewMapper partnerVViewMapper,
			SortDirectionViewMapper sortDirectionViewMapper,
			PartnerVSortFieldViewMapper partnerVSortFieldViewMapper
	) {
		this.delegate = delegate;
		this.partnerViewMapper = partnerViewMapper;
		this.partnerVViewMapper = partnerVViewMapper;
		this.sortDirectionViewMapper = sortDirectionViewMapper;
		this.partnerVSortFieldViewMapper = partnerVSortFieldViewMapper;
	}

	public PartnerVPageView searchPartners(
			int page,
			int pageSize,
			PartnerVSortFieldView sortFieldView,
			SortDirectionView sortDirectionView,
			Long partnerTagId
	) {
		PartnerVSortFieldDto sortFieldDto = partnerVSortFieldViewMapper.fromView(sortFieldView);
		SortDirectionDto sortDirectionDto = sortDirectionViewMapper.fromView(sortDirectionView);
		PartnerVPageDto all = delegate.searchPartners(page, pageSize, sortFieldDto, sortDirectionDto, partnerTagId);
		return partnerVViewMapper.toView(all);
	}

	public void deletePartner(Long partnerId) {
		delegate.deletePartner(partnerId);
	}

	public Optional<PartnerView> getPartnerById(long partnerId) {
		return delegate
				.getPartnerById(partnerId)
				.map(partnerViewMapper::toView);
	}

	public PartnerView createOrUpdate(CreateOrUpdatePartnerRequestView dto, Long partnerId) {
		PartnerDto partnerDto = delegate.createOrUpdate(partnerViewMapper.toDto(dto), partnerId);
		return partnerViewMapper.toView(partnerDto);
	}

}
