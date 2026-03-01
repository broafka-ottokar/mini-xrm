package com.example.minixrm.backend.core.facade;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.example.minixrm.backend.core.domain.entity.Partner;
import com.example.minixrm.backend.core.facade.dto.CreateOrUpdatePartnerDto;
import com.example.minixrm.backend.core.facade.dto.PartnerStatusDto;
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
public class PartnerDtoFacadeTest {

	@Autowired
	private PartnerDtoFacade partnerDtoFacade;

	@MockBean
	private PartnerJpaRepository partnerRepository;
	
	@MockBean
	private PartnerVJpaRepository partnerVRepository;

	@MockBean
	private PartnerTagJpaRepository partnerTagRepository;
	
	@MockBean
	private PersonResponsibleReportJpaRepository personResponsibleReportRepository;

	@MockBean
	private ActivityJpaRepository activityRepository;

	@Test
	public void create_succeeds() {
		when(partnerTagRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

		CreateOrUpdatePartnerDto dto = new CreateOrUpdatePartnerDto(
				"Test Partner",
				"123456789",
				"Test Headquarters",
				PartnerStatusDto.ACTIVE,
				Collections.emptyList()
		);
		doAnswer(invocation -> {
			Partner saved = invocation.getArgument(0);
			saved.setId(1L);
			return saved;
		}).when(partnerRepository).save(any(Partner.class));
		
		partnerDtoFacade.createOrUpdate(dto, null);

		verify(partnerRepository, times(1)).save(any(Partner.class));
	}
	
	@Test
	public void create_checksStatus_andFails() {
		when(partnerTagRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

		CreateOrUpdatePartnerDto dto = new CreateOrUpdatePartnerDto(
				"Test Partner",
				"123456789",
				"Test Headquarters",
				null,
				Collections.emptyList()
		);
		
		try {
			partnerDtoFacade.createOrUpdate(dto, null);
		} catch (ConstraintViolationException e) {
			// expected
		} finally {
			verify(partnerRepository, times(0)).save(any(Partner.class));
		}
	}
	
}