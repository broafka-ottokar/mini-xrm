package com.example.minixrm.backend.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import com.example.minixrm.backend.web.util.StringToEnumConverterFactory;

@Configuration
public class ConversionServiceConfig {

	@Bean(name = "mvcConversionService2")
	public FormattingConversionService mvcConversionService2() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverterFactory(new StringToEnumConverterFactory());
		return conversionService;
	}
}
