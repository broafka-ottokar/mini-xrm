package com.example.minixrm.backend.core.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerStatus;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Validated
public class ActivityService {
	
	private final ActivityJpaRepository activityRepository;
	
	public ActivityService(
			ActivityJpaRepository activityRepository
	) {
		this.activityRepository = activityRepository;
	}

	public Optional<Activity> getActivityById(Long id) {
		return activityRepository
				.findById(id);
	}
	
	public Page<Activity> findActivitiesByPartner(long partnerId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Activity> all = activityRepository.findAll(pageable);
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
	@Retry(name = "optimisticLockingRetry", fallbackMethod = "handleOptimisticLockingFailure")
	public void deleteActivity(Long activityId) {
		Activity activity = activityRepository.findById(activityId)
				.orElseThrow(() -> new EntityNotFoundException("Activity not found with id: " + activityId));
		activityRepository.delete(activity);
	}

	protected void handleOptimisticLockingFailure(Long activityId, Throwable t) {
		throw ApplicationException.concurrentModification(activityId, t);
	}
	
}