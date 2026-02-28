package com.example.minixrm.backend.web.openapi.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;
import java.io.Serializable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import jakarta.annotation.Generated;

/**
 * CreateOrUpdatePartnerRequestView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class CreateOrUpdatePartnerRequestView implements Serializable {

  private static final long serialVersionUID = 1L;

  private String name;

  private String taxNumber;

  private @Nullable String headquarters;

  private PartnerStatusView status;

  @Valid
  private List<@Min(1L) @Max(9223372036854775807L)Long> tagIds = new ArrayList<>();

  public CreateOrUpdatePartnerRequestView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateOrUpdatePartnerRequestView(String name, String taxNumber, PartnerStatusView status, List<@Min(1L) @Max(9223372036854775807L)Long> tagIds) {
    this.name = name;
    this.taxNumber = taxNumber;
    this.status = status;
    this.tagIds = tagIds;
  }

  public CreateOrUpdatePartnerRequestView name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @NotNull @Size(min = 0, max = 150) 
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CreateOrUpdatePartnerRequestView taxNumber(String taxNumber) {
    this.taxNumber = taxNumber;
    return this;
  }

  /**
   * Get taxNumber
   * @return taxNumber
   */
  @NotNull @Size(min = 0, max = 20) 
  @JsonProperty("tax_number")
  public String getTaxNumber() {
    return taxNumber;
  }

  public void setTaxNumber(String taxNumber) {
    this.taxNumber = taxNumber;
  }

  public CreateOrUpdatePartnerRequestView headquarters(@Nullable String headquarters) {
    this.headquarters = headquarters;
    return this;
  }

  /**
   * Get headquarters
   * @return headquarters
   */
  @Size(min = 0, max = 150) 
  @JsonProperty("headquarters")
  public @Nullable String getHeadquarters() {
    return headquarters;
  }

  public void setHeadquarters(@Nullable String headquarters) {
    this.headquarters = headquarters;
  }

  public CreateOrUpdatePartnerRequestView status(PartnerStatusView status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
   */
  @NotNull @Valid 
  @JsonProperty("status")
  public PartnerStatusView getStatus() {
    return status;
  }

  public void setStatus(PartnerStatusView status) {
    this.status = status;
  }

  public CreateOrUpdatePartnerRequestView tagIds(List<@Min(1L) @Max(9223372036854775807L)Long> tagIds) {
    this.tagIds = tagIds;
    return this;
  }

  public CreateOrUpdatePartnerRequestView addTagIdsItem(Long tagIdsItem) {
    if (this.tagIds == null) {
      this.tagIds = new ArrayList<>();
    }
    this.tagIds.add(tagIdsItem);
    return this;
  }

  /**
   * Get tagIds
   * @return tagIds
   */
  @NotNull 
  @JsonProperty("tagIds")
  public List<@Min(1L) @Max(9223372036854775807L)Long> getTagIds() {
    return tagIds;
  }

  public void setTagIds(List<@Min(1L) @Max(9223372036854775807L)Long> tagIds) {
    this.tagIds = tagIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateOrUpdatePartnerRequestView createOrUpdatePartnerRequestView = (CreateOrUpdatePartnerRequestView) o;
    return Objects.equals(this.name, createOrUpdatePartnerRequestView.name) &&
        Objects.equals(this.taxNumber, createOrUpdatePartnerRequestView.taxNumber) &&
        Objects.equals(this.headquarters, createOrUpdatePartnerRequestView.headquarters) &&
        Objects.equals(this.status, createOrUpdatePartnerRequestView.status) &&
        Objects.equals(this.tagIds, createOrUpdatePartnerRequestView.tagIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, taxNumber, headquarters, status, tagIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateOrUpdatePartnerRequestView {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    taxNumber: ").append(toIndentedString(taxNumber)).append("\n");
    sb.append("    headquarters: ").append(toIndentedString(headquarters)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    tagIds: ").append(toIndentedString(tagIds)).append("\n");
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

