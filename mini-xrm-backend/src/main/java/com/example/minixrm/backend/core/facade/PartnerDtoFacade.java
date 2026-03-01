package com.example.minixrm.backend.core.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.domain.PartnerVSortField;
import com.example.minixrm.backend.core.domain.SortDirection;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.domain.entity.PartnerV;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVPageDto;
import com.example.minixrm.backend.core.facade.dto.PartnerVSortFieldDto;
import com.example.minixrm.backend.core.facade.dto.SortDirectionDto;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.service.PartnerService;
import com.example.minixrm.backend.core.util.mapper.PartnerDtoMapper;
import com.example.minixrm.backend.core.util.mapper.PartnerVDtoMapper;
import com.example.minixrm.backend.core.util.mapper.PartnerVSortFieldDtoMapper;
import com.example.minixrm.backend.core.util.mapper.SortDirectionDtoMapper;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Component
@Validated
public class PartnerDtoFacade {
	
	private final PartnerService partnerService;
	private final PartnerJpaRepository partnerRepository;
	private final PartnerTagJpaRepository partnerTagRepository;
	private final PartnerDtoMapper partnerDtoMapper;
	private final PartnerVSortFieldDtoMapper partnerVSortFieldDtoMapper;
	private final SortDirectionDtoMapper sortDirectionDtoMapper;
	private final PartnerVDtoMapper partnerVDtoMapper;
	
	public PartnerDtoFacade(
			PartnerService partnerService,
			PartnerJpaRepository partnerRepository,
			PartnerTagJpaRepository partnerTagRepository,
			PartnerDtoMapper partnerDtoMapper,
			PartnerVSortFieldDtoMapper partnerVSortFieldDtoMapper,
			SortDirectionDtoMapper sortDirectionDtoMapper,
			PartnerVDtoMapper partnerVDtoMapper
	) {
		this.partnerService = partnerService;
		this.partnerRepository = partnerRepository;
		this.partnerTagRepository = partnerTagRepository;
		this.partnerDtoMapper = partnerDtoMapper;
		this.partnerVSortFieldDtoMapper = partnerVSortFieldDtoMapper;
		this.sortDirectionDtoMapper = sortDirectionDtoMapper;
		this.partnerVDtoMapper = partnerVDtoMapper;
	}

	public Optional<PartnerDto> getPartnerById(Long id) {
		return partnerService
				.getPartnerById(id)
				.map(partnerDtoMapper::toDto);
	}
	
	public PartnerVPageDto searchPartners(
			int page,
			int pageSize,
			PartnerVSortFieldDto sortFieldDto,
			SortDirectionDto sortDirectionDto,
			Long partnerTagId
	) {
		PartnerVSortField sortField = partnerVSortFieldDtoMapper.fromDto(sortFieldDto);
		SortDirection sortDirection = sortDirectionDtoMapper.fromDto(sortDirectionDto);
		Page<PartnerV> all = partnerService.searchPartners(page, pageSize, sortField, sortDirection, partnerTagId);
		return partnerVDtoMapper.toDto(all);
	}

	@Transactional
	public void deletePartner(Long partnerId) {
		partnerService.deletePartner(partnerId);
	}

	@Transactional
	@Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
	public PartnerDto createOrUpdate(@Valid CreateOrUpdatePartnerDto dto, Long id) {
		boolean update = (id != null);
		Partner existing;
		if (update) {
			existing = partnerRepository.findById(id)
				.orElseThrow(() -> ApplicationException.entityNotFound(id));
		} else {
			existing = null;
		}
		List<PartnerTag> partnerTags = partnerTagRepository.findAllById(dto.getPartnerTagIds());
		Partner partner = partnerDtoMapper.toEntity(dto, existing, partnerTags);
		partner = partnerService.createOrUpdate(partner);
		return partnerDtoMapper.toDto(partner);
	}

}