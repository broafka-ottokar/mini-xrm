package com.example.minixrm.backend.core.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
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

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerStatusDto;
import com.example.minixrm.backend.core.facade.util.ApplicationException;
import com.example.minixrm.backend.core.facade.util.ErrorCode;
import com.example.minixrm.backend.core.repository.ActivityJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerJpaRepository;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;
import com.example.minixrm.backend.core.repository.PersonResponsibleReportJpaRepository;

@SpringBootTest
@ActiveProfiles("unittest")
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class,
		LiquibaseAutoConfiguration.class
})
public class PartnerDtoFacadeRetryTest {

	@Autowired
	private PartnerDtoFacade partnerDtoFacade;

	@MockBean
	private PartnerJpaRepository partnerRepository;

	@MockBean
	private PartnerTagJpaRepository partnerTagRepository;
	
	@MockBean
	private PersonResponsibleReportJpaRepository personResponsibleReportRepository;

	@MockBean
	private ActivityJpaRepository activityRepository;

	@Test
	public void create_retriesOnOptimisticLockingFailure_andSucceeds() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		when(partnerTagRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test"))
			.doReturn(partner)
			.when(partnerRepository)
			.save(partner);

		CreateOrUpdatePartnerDto dto = new CreateOrUpdatePartnerDto(
				"Test Partner",
				"123456789",
				"Test Headquarters",
				PartnerStatusDto.ACTIVE,
				Collections.emptyList()
		);
		
		partnerDtoFacade.createOrUpdate(dto, partnerId);

		verify(partnerRepository, times(2)).save(partner);
	}
	
	@Test
	public void create_retriesOnOptimisticLockingFailure_fails() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		when(partnerTagRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test 1"))
			.doThrow(new OptimisticLockingFailureException("optimistic lock failure test 2"))
			.doThrow(new OptimisticLockingFailureException("optimistic lock failure test 3"))
			.when(partnerRepository)
			.save(partner);

		CreateOrUpdatePartnerDto dto = new CreateOrUpdatePartnerDto(
				"Test Partner",
				"123456789",
				"Test Headquarters",
				PartnerStatusDto.ACTIVE,
				Collections.emptyList()
		);
		
		try {
			partnerDtoFacade.createOrUpdate(dto, partnerId);
		} catch (ApplicationException ex) {
			assertEquals(ErrorCode.CONCURRENT_MODIFICATION, ex.getErrorCode());
		} finally {
			verify(partnerRepository, times(3)).save(partner);
		}
	}
	
	@Test
	public void deletePartner_retriesOnOptimisticLockingFailure_andSucceeds() {
		Long partnerId = 1L;
		Partner partner = new Partner();
		partner.setId(partnerId);

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.of(partner));

		doThrow(new OptimisticLockingFailureException("optimistic lock failure test"))
			.doNothing()
			.when(partnerRepository)
			.delete(partner);

		partnerDtoFacade.deletePartner(partnerId);

		verify(partnerRepository, times(2)).delete(partner);
	}
	
}