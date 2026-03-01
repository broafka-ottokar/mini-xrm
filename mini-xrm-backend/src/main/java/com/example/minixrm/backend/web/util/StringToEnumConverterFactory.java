package com.example.minixrm.backend.web.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * ConverterFactory that converts a String to an Enum using a static fromValue(String)
 * method when present on the enum, otherwise falls back to case-insensitive Enum.valueOf.
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

	private static final Logger log = LoggerFactory.getLogger(StringToEnumConverterFactory.class);

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToEnum<>(targetType);
	}

	private static class StringToEnum<T extends Enum> implements Converter<String, T> {
		private final Class<T> enumType;
		private Method fromValueMethod;

		StringToEnum(Class<T> enumType) {
		this.enumType = enumType;
		try {
		this.fromValueMethod = enumType.getMethod("fromValue", String.class);
		} catch (NoSuchMethodException e) {
		this.fromValueMethod = null;
		}
		}

		@Override
		public T convert(String source) {
		if (source == null || source.isEmpty()) {
		return null;
		}

		if (log.isTraceEnabled()) {
		log.trace("Converting string '{}' to enum type {}", source, enumType.getName());
		}

		// Try fromValue(String) if present
		if (fromValueMethod != null) {
		try {
		Object result = fromValueMethod.invoke(null, source);
		return enumType.cast(result);
		} catch (IllegalAccessException | InvocationTargetException e) {
		throw new IllegalArgumentException("Failed to convert String to enum " + enumType, e);
		}
		}

		// Fallback to case-insensitive valueOf - use raw cast to satisfy Enum.valueOf signature
		try {
		return (T) Enum.valueOf((Class) enumType, source.toUpperCase());
		} catch (IllegalArgumentException e) {
		// Try exact match (maybe caller passed the declared enum name)
		try {
		return (T) Enum.valueOf((Class) enumType, source);
		} catch (IllegalArgumentException ex) {
		throw new IllegalArgumentException("No enum constant " + enumType.getName() + "." + source);
		}
		}
		}
	}
}