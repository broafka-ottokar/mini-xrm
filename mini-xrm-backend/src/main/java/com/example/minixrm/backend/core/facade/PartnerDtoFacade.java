package com.example.minixrm.backend.core.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerPageDto;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.service.PartnerService;
import com.example.minixrm.backend.core.util.mapper.PartnerDtoMapper;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Component
public class PartnerDtoFacade {
	
	private final PartnerService partnerService;
	private final PartnerJpaRepository partnerRepository;
	private final PartnerTagJpaRepository partnerTagRepository;
	private final PartnerDtoMapper partnerDtoMapper;
	
	public PartnerDtoFacade(
			PartnerService partnerService,
			PartnerJpaRepository partnerRepository,
			PartnerTagJpaRepository partnerTagRepository,
			PartnerDtoMapper partnerDtoMapper
	) {
		this.partnerService = partnerService;
		this.partnerRepository = partnerRepository;
		this.partnerTagRepository = partnerTagRepository;
		this.partnerDtoMapper = partnerDtoMapper;
	}

	public Optional<PartnerDto> getPartnerById(Long id) {
		return partnerService
				.getPartnerById(id)
				.map(partnerDtoMapper::toDto);
	}
	
	public PartnerPageDto searchPartners(int page, int pageSize, Long partnerTagId) {
		Page<Partner> all = partnerService.searchPartners(page, pageSize, partnerTagId);
		return partnerDtoMapper.toDto(all);
	}

	@Transactional
	public void deletePartner(Long partnerId) {
		partnerService.deletePartner(partnerId);
	}

	@Transactional
	@Retry(name = "optimisticLockingRetry", fallbackMethod = "handleOptimisticLockingFailure")
	public void createOrUpdate(@Valid CreateOrUpdatePartnerDto dto, Long id) {
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
		partnerService.createOrUpdate(partner);
	}

	protected void handleOptimisticLockingFailure(CreateOrUpdatePartnerDto dto, Long id, Throwable t) {
		throw ApplicationException.concurrentModification(id, t);
	}
	
}