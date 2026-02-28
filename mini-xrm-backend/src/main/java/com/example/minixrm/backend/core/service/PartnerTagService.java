package com.example.minixrm.backend.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.minixrm.backend.core.domain.entity.PartnerTag;
import com.example.minixrm.backend.core.repository.PartnerTagJpaRepository;

@Service
@Validated
public class PartnerTagService {
	
	private final PartnerTagJpaRepository partnerTagRepository;
	
	public PartnerTagService(
			PartnerTagJpaRepository partnerTagRepository
	) {
		this.partnerTagRepository = partnerTagRepository;
	}

	public List<PartnerTag> findAll() {
		List<PartnerTag> all = partnerTagRepository.findAll();
		return all;
	}
	
}
