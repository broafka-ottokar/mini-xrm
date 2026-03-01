package com.example.minixrm.backend.core.service;

import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.domain.PartnerVSortField;
import com.example.minixrm.backend.core.domain.SortDirection;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.domain.entity.PartnerV;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerVJpaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class PartnerService {
	
	private final PartnerJpaRepository partnerRepository;
	private final PartnerVJpaRepository partnerVRepository;
	private final PartnerTagJpaRepository partnerTagRepository;
	
	public PartnerService(
			PartnerJpaRepository partnerRepository,
			PartnerVJpaRepository partnerVRepository,
			PartnerTagJpaRepository partnerTagRepository
	) {
		this.partnerRepository = partnerRepository;
		this.partnerVRepository = partnerVRepository;
		this.partnerTagRepository = partnerTagRepository;
	}

	public Optional<Partner> getPartnerById(Long id) {
		return partnerRepository.findById(id);
	}
	
	public Page<PartnerV> searchPartners(
			int page,
			int pageSize,
			PartnerVSortField sortField,
			SortDirection sortDirection,
			Long partnerTagId
	) {
		Pageable pageable;
		if ((sortField != null) && (sortDirection != null)) {
			Direction direction = sortDirection.toDirection();
			pageable = PageRequest.of(page, pageSize, direction, sortField.getFieldName());
		} else {
			pageable = PageRequest.of(page, pageSize);
		}
		Page<PartnerV> all;
		if (partnerTagId == null) {
			all = partnerVRepository.findAll(pageable);
		} else {
			Optional<PartnerTag> partnerTag = partnerTagRepository
					.findById(partnerTagId);
			if (partnerTag.isEmpty()) {
				throw ApplicationException.partnerTagNotFound(partnerTagId);
			} else {
				all = partnerVRepository.findByPartnerTags(pageable, partnerTag.get());
			}
		}
		return all;
	}
	
	@Transactional
	@Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
	public void deletePartner(Long partnerId) {
		Partner partner = partnerRepository.findById(partnerId)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with id: " + partnerId));
		partnerRepository.delete(partner);
	}
	
	@Transactional
	public void createOrUpdate(@Valid Partner entity) {
		partnerRepository.save(entity);
	}

}