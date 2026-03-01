package com.example.minixrm.backend.core.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerStatus;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdateActivityDto;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerVJpaRepository;
import com.example.minixrm.backend.core.repository.PersonResponsibleReportJpaRepository;

@SpringBootTest
@ActiveProfiles("unittest")
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		LiquibaseAutoConfiguration.class
})
public class ActivityDtoFacadeRetryTest {

	@Autowired
	private ActivityDtoFacade activityDtoFacade;

	@MockBean
	private PartnerJpaRepository partnerRepository;
	
	@MockBean
	private PartnerVJpaRepository partnerVRepository;
	
	@MockBean
	private PartnerTagJpaRepository partnerTagRepository;

	@MockBean
	private ActivityJpaRepository activityRepository;
	
	@MockBean
	private PersonResponsibleReportJpaRepository personResponsibleReportRepository;

	@Test
	public void create_retriesOnOptimisticLockingFailure_andSucceeds() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);
		partner.setStatus(PartnerStatus.ACTIVE);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test"))
			.doAnswer(invocation -> invocation.getArgument(0))
			.when(activityRepository).save(any(Activity.class));

		CreateOrUpdateActivityDto dto = new CreateOrUpdateActivityDto(
				"Subject",
				"Type",
				"Description",
				60,
				"Me",
				partnerId
		);

		activityDtoFacade.createOrUpdateActivity(null, dto);

		verify(activityRepository, times(2)).save(any(Activity.class));
	}

	@Test
	public void create_retriesOnOptimisticLockingFailure_fails() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);
		partner.setStatus(PartnerStatus.ACTIVE);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test 1"))
			.doThrow(new OptimisticLockingFailureException("optimistic lock failure test 2"))
			.doThrow(new OptimisticLockingFailureException("optimistic lock failure test 3"))
			.when(activityRepository).save(any(Activity.class));

		CreateOrUpdateActivityDto dto = new CreateOrUpdateActivityDto(
				"Subject",
				"Type",
				"Description",
				60,
				"Me",
				partnerId
		);

		try {
			activityDtoFacade.createOrUpdateActivity(null, dto);
		} catch (OptimisticLockingFailureException ex) {
			assertEquals("optimistic lock failure test 3", ex.getMessage());
		} finally {
			verify(activityRepository, times(3)).save(any(Activity.class));
		}
	}

	@Test
	public void deleteActivity_retriesOnOptimisticLockingFailure_andSucceeds() {
		Long activityId = 1L;
		Activity activity = new Activity();
		activity.setId(activityId);

		when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test"))
			.doNothing()
			.when(activityRepository).delete(activity);

		activityDtoFacade.deleteActivity(activityId);

		verify(activityRepository, times(2)).delete(activity);
	}

}