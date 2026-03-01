package com.example.minixrm.backend.core.facade;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
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
import org.springframework.test.context.ActiveProfiles;

import com.example.minixrm.backend.core.domain.entity.Activity;
import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.domain.entity.PartnerStatus;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdateActivityDto;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.facade.util.ErrorCode;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerVJpaRepository;
import com.example.minixrm.backend.core.repository.PersonResponsibleReportJpaRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@ActiveProfiles("unittest")
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		LiquibaseAutoConfiguration.class
})
public class ActivityDtoFacadeTest {

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
	public void create_succeeds() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);
		partner.setStatus(PartnerStatus.ACTIVE);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		doAnswer(invocation -> {
				Activity activity = invocation.getArgument(0);
				activity.setId(1L);
				return activity;
			})
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

		verify(activityRepository, times(1)).save(any(Activity.class));
	}
	
	@Test
	public void create_checksPartnerStatus_andFails() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);
		partner.setStatus(PartnerStatus.INACTIVE);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		doAnswer(invocation -> invocation.getArgument(0))
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
		} catch (ApplicationException e) {
			assertTrue(ErrorCode.PARTNER_INACTIVE.equals(e.getErrorCode()));
		} finally {
			verify(activityRepository, times(0)).save(any(Activity.class));
		}
	}

	@Test
	public void create_checksPersonResponsible_andFails() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);
		partner.setStatus(PartnerStatus.ACTIVE);
		
		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));
		
		doAnswer(invocation -> invocation.getArgument(0))
		.when(activityRepository).save(any(Activity.class));
		
		CreateOrUpdateActivityDto dto = new CreateOrUpdateActivityDto(
				"Subject",
				"Type",
				"Description",
				60,
				"",
				partnerId
				);
		
		try {
			activityDtoFacade.createOrUpdateActivity(null, dto);
		} catch (ConstraintViolationException e) {
			// Expected
		} finally {
			verify(activityRepository, times(0)).save(any(Activity.class));
		}
	}
	
}