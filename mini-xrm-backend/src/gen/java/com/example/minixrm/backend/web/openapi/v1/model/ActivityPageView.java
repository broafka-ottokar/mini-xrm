package com.example.minixrm.backend.web.openapi.v1.model;

import java.net.URI;
import java.util.Objects;
import com.example.minixrm.backend.web.openapi.v1.model.ActivityView;
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
 * ActivityPageView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class ActivityPageView implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long totalElements;

  private Integer totalPages;

  private Integer currentPage;

  private Integer pageSize;

  @Valid
  private List<@Valid ActivityView> content = new ArrayList<>();

  public ActivityPageView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ActivityPageView(Long totalElements, Integer totalPages, Integer currentPage, Integer pageSize, List<@Valid ActivityView> content) {
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.content = content;
  }

  public ActivityPageView totalElements(Long totalElements) {
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

  public ActivityPageView totalPages(Integer totalPages) {
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

  public ActivityPageView currentPage(Integer currentPage) {
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

  public ActivityPageView pageSize(Integer pageSize) {
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

  public ActivityPageView content(List<@Valid ActivityView> content) {
    this.content = content;
    return this;
  }

  public ActivityPageView addContentItem(ActivityView contentItem) {
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
  public List<@Valid ActivityView> getContent() {
    return content;
  }

  public void setContent(List<@Valid ActivityView> content) {
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
    ActivityPageView activityPageView = (ActivityPageView) o;
    return Objects.equals(this.totalElements, activityPageView.totalElements) &&
        Objects.equals(this.totalPages, activityPageView.totalPages) &&
        Objects.equals(this.currentPage, activityPageView.currentPage) &&
        Objects.equals(this.pageSize, activityPageView.pageSize) &&
        Objects.equals(this.content, activityPageView.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalElements, totalPages, currentPage, pageSize, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActivityPageView {\n");
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

