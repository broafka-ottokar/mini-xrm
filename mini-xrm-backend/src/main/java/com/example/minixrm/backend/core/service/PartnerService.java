package com.example.minixrm.backend.core.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class PartnerService {
	
	private final PartnerJpaRepository partnerRepository;
	private final PartnerTagJpaRepository partnerTagRepository;
	
	public PartnerService(
			PartnerJpaRepository repository,
			PartnerTagJpaRepository partnerTagRepository
	) {
		this.partnerRepository = repository;
		this.partnerTagRepository = partnerTagRepository;
	}

	public Optional<Partner> getPartnerById(Long id) {
		return partnerRepository.findById(id);
	}
	
	public Page<Partner> searchPartners(int page, int pageSize, Long partnerTagId) {
		Pageable pageable = Pageable.ofSize(pageSize).withPage(page);
		Page<Partner> all;
		if (partnerTagId == null) {
			all = partnerRepository.findAll(pageable);
		} else {
			Optional<PartnerTag> partnerTag = partnerTagRepository
					.findById(partnerTagId);
			if (partnerTag.isEmpty()) {
				throw ApplicationException.partnerTagNotFound(partnerTagId);
			} else {
				all = partnerRepository.findByPartnerTags(pageable, partnerTag.get());
			}
		}
		return all;
	}
	
	@Transactional
	@Retry(name = "optimisticLockingRetry", fallbackMethod = "handleOptimisticLockingFailure")
	public void deletePartner(Long partnerId) {
		Partner partner = partnerRepository.findById(partnerId)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with id: " + partnerId));
		partnerRepository.delete(partner);
	}
	
	@Transactional
	public void createOrUpdate(@Valid Partner entity) {
		partnerRepository.save(entity);
	}

	protected void handleOptimisticLockingFailure(Long partnerId, Throwable t) {
		throw ApplicationException.concurrentModification(partnerId, t);
	}

}