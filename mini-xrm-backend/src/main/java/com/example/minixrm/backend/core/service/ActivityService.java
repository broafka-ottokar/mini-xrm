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

import com.example.minixrm.backend.core.domain.ActivitySortField;
import com.example.minixrm.backend.core.domain.SortDirection;
import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerStatus;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Validated
public class ActivityService {
	
	private final ActivityJpaRepository activityRepository;
	private final PartnerJpaRepository partnerRepository;
	
	public ActivityService(
			ActivityJpaRepository activityRepository,
			PartnerJpaRepository partnerRepository
	) {
		this.activityRepository = activityRepository;
		this.partnerRepository = partnerRepository;
	}

	public Optional<Activity> getActivityById(Long id) {
		return activityRepository
				.findById(id);
	}
	
	public Page<Activity> findActivitiesByPartner(
			long partnerId,
			int page,
			int pageSize,
			ActivitySortField sortField,
			SortDirection sortDirection
	) {
		Pageable pageable;
		if ((sortField != null) && (sortDirection != null)) {
			Direction direction = sortDirection.toDirection();
			pageable = PageRequest.of(page, pageSize, direction, sortField.getFieldName());
		} else {
			pageable = PageRequest.of(page, pageSize);
		}
		Partner partner = partnerRepository
				.findById(partnerId)
				.orElseThrow(() -> ApplicationException.entityNotFound(partnerId));
		Page<Activity> all = activityRepository.findByPartner(partner, pageable);
		return all;
	}
	
	@Transactional
	public void createOrUpdateActivity(Activity entity) {
		boolean update = (entity.getId() != null);
		checkPartnerActive(entity, update);
		activityRepository.save(entity);
	}

	private void checkPartnerActive(Activity entity, boolean update) {
		Partner partner = entity.getPartner();
		boolean partnerActive = (partner.getStatus() == PartnerStatus.ACTIVE);
		if (!update && !partnerActive) {
			throw ApplicationException.partnerInactive(partner.getId());
		}
	}

	@Transactional
	@Retryable(retryFor = OptimisticLockingFailureException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
	public void deleteActivity(Long activityId) {
		Activity activity = activityRepository.findById(activityId)
				.orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));
		activityRepository.delete(activity);
	}

}