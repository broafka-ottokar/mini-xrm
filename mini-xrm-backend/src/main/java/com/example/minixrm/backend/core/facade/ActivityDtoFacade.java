package com.example.minixrm.backend.core.facade;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.facade.dto.ActivityDto;
import com.example.minixrm.backend.core.facade.dto.ActivityPageDto;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdateActivityDto;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.service.ActivityService;
import com.example.minixrm.backend.core.util.mapper.ActivityDtoMapper;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Component
public class ActivityDtoFacade {
	
	private final ActivityService activityService;
	private final ActivityDtoMapper activityDtoMapper;
	private final ActivityJpaRepository activityRepository;
	private final PartnerJpaRepository partnerRepository;
	
	public ActivityDtoFacade(
			ActivityService activityService,
			ActivityDtoMapper activityDtoMapper,
			ActivityJpaRepository activityRepository,
			PartnerJpaRepository partnerRepository
	) {
		this.activityService = activityService;
		this.activityDtoMapper = activityDtoMapper;
		this.activityRepository = activityRepository;
		this.partnerRepository = partnerRepository;
	}

	public ActivityDto getActivityById(Long id) {
		Activity entity = activityService.getActivityById(id)
				.orElseThrow(() -> ApplicationException.entityNotFound(id));
		return activityDtoMapper.toDto(entity);
	}
	
	@Transactional
	@Retry(name = "optimisticLockingRetry", fallbackMethod = "handleOptimisticLockingFailure")
	public void createOrUpdateActivity(Long activityId, @Valid CreateOrUpdateActivityDto dto) {
		boolean update = (activityId != null);
		Activity existing;
		if (update) {
			existing = activityRepository.findById(activityId)
					.orElseThrow(() -> ApplicationException.entityNotFound(activityId));
		} else {
			existing = null;
		}
		long partnerId = dto.getPartnerId();
		Partner partner = partnerRepository
				.findById(partnerId)
				.orElseThrow(() -> ApplicationException.partnerNotFound(partnerId));
		Activity entity = activityDtoMapper.fromDto(dto, existing, partner);
		activityService.createOrUpdateActivity(entity);
	}
	
	public void deleteActivity(Long activityId) {
		activityService.deleteActivity(activityId);
	}
	
	public ActivityPageDto findActivitiesByPartner(
			Long partnerId,
			Integer page,
			Integer pageSize
	) {
		Page<Activity> all = activityService.findActivitiesByPartner(partnerId, page, pageSize);
		return activityDtoMapper.toDto(all);
	}

	protected void handleOptimisticLockingFailure(Long activityId, CreateOrUpdateActivityDto dto, Throwable t) {
		throw ApplicationException.concurrentModification(activityId, t);
	}
	
}
