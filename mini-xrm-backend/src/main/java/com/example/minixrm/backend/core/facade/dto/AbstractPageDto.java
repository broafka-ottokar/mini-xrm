package com.example.minixrm.backend.core.facade.dto;

import java.util.List;

public abstract class AbstractPageDto<T> {

	protected final long totalElements;
	protected final int totalPages;
	protected final int currentPage;
	protected final int pageSize;
	private final List<T> content;

	public AbstractPageDto(
			long totalElements,
			int totalPages,
			int currentPage,
			int pageSize,
			List<T> content
			) {
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.content = content;
	}

	public List<T> getContent() {
		return content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

}