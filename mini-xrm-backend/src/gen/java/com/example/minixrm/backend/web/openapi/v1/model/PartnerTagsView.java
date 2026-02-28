package com.example.minixrm.backend.web.openapi.v1.model;

import java.net.URI;
import java.util.Objects;
import com.example.minixrm.backend.web.openapi.v1.model.PartnerTagView;
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
 * PartnerTagsView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class PartnerTagsView implements Serializable {

  private static final long serialVersionUID = 1L;

  @Valid
  private List<@Valid PartnerTagView> content = new ArrayList<>();

  public PartnerTagsView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PartnerTagsView(List<@Valid PartnerTagView> content) {
    this.content = content;
  }

  public PartnerTagsView content(List<@Valid PartnerTagView> content) {
    this.content = content;
    return this;
  }

  public PartnerTagsView addContentItem(PartnerTagView contentItem) {
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
  public List<@Valid PartnerTagView> getContent() {
    return content;
  }

  public void setContent(List<@Valid PartnerTagView> content) {
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
    PartnerTagsView partnerTagsView = (PartnerTagsView) o;
    return Objects.equals(this.content, partnerTagsView.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartnerTagsView {\n");
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

