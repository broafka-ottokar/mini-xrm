package com.example.minixrm.backend.web.openapi.v1.model;

import java.net.URI;
import java.util.Objects;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerVView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PartnerVPageView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class PartnerVPageView implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long totalElements;

  private Integer totalPages;

  private Integer currentPage;

  private Integer pageSize;

  @Valid
  private List<@Valid PartnerVView> content = new ArrayList<>();

  public PartnerVPageView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PartnerVPageView(Long totalElements, Integer totalPages, Integer currentPage, Integer pageSize, List<@Valid PartnerVView> content) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.content = content;
  }

  public PartnerVPageView totalElements(Long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  /**
   * Get totalElements
   * minimum: 0
   * @return totalElements
   */
  @NotNull @Min(value = 0L) 
  @JsonProperty("totalElements")
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  public PartnerVPageView totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * minimum: 0
   * @return totalPages
   */
  @NotNull @Min(value = 0) 
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public PartnerVPageView currentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return this;
  }

  /**
   * The current page number (0-based)
   * minimum: 0
   * @return currentPage
   */
  @NotNull @Min(value = 0) 
  @JsonProperty("currentPage")
  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public PartnerVPageView pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   * minimum: 1
   * @return pageSize
   */
  @NotNull @Min(value = 1) 
  @JsonProperty("pageSize")
  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public PartnerVPageView content(List<@Valid PartnerVView> content) {
    this.content = content;
    return this;
  }

  public PartnerVPageView addContentItem(PartnerVView contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   * @return content
   */
  @NotNull @Valid 
  @JsonProperty("content")
  public List<@Valid PartnerVView> getContent() {
    return content;
  }

  public void setContent(List<@Valid PartnerVView> content) {
    this.content = content;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartnerVPageView partnerVPageView = (PartnerVPageView) o;
    return Objects.equals(this.totalElements, partnerVPageView.totalElements) &&
        Objects.equals(this.totalPages, partnerVPageView.totalPages) &&
        Objects.equals(this.currentPage, partnerVPageView.currentPage) &&
        Objects.equals(this.pageSize, partnerVPageView.pageSize) &&
        Objects.equals(this.content, partnerVPageView.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalElements, totalPages, currentPage, pageSize, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartnerVPageView {\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(@Nullable Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

