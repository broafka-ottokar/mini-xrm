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
 * PartnerView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class PartnerView implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String name;

  private @Nullable String taxNumber;

  private String headquarters;

  private PartnerStatusView status;

  @Valid
  private List<@Valid PartnerTagView> tags = new ArrayList<>();

  public PartnerView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PartnerView(Long id, String name, String headquarters, PartnerStatusView status, List<@Valid PartnerTagView> tags) {
    this.id = id;
    this.name = name;
    this.headquarters = headquarters;
    this.status = status;
    this.tags = tags;
  }

  public PartnerView id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * minimum: 1
   * maximum: 9223372036854775807
   * @return id
   */
  @NotNull @Min(value = 1L) @Max(value = 9223372036854775807L) 
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PartnerView name(String name) {
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

  public PartnerView taxNumber(@Nullable String taxNumber) {
    this.taxNumber = taxNumber;
    return this;
  }

  /**
   * Get taxNumber
   * @return taxNumber
   */
  @Size(min = 0, max = 20) 
  @JsonProperty("taxNumber")
  public @Nullable String getTaxNumber() {
    return taxNumber;
  }

  public void setTaxNumber(@Nullable String taxNumber) {
    this.taxNumber = taxNumber;
  }

  public PartnerView headquarters(String headquarters) {
    this.headquarters = headquarters;
    return this;
  }

  /**
   * Get headquarters
   * @return headquarters
   */
  @NotNull @Size(min = 0, max = 150) 
  @JsonProperty("headquarters")
  public String getHeadquarters() {
    return headquarters;
  }

  public void setHeadquarters(String headquarters) {
    this.headquarters = headquarters;
  }

  public PartnerView status(PartnerStatusView status) {
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

  public PartnerView tags(List<@Valid PartnerTagView> tags) {
    this.tags = tags;
    return this;
  }

  public PartnerView addTagsItem(PartnerTagView tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Get tags
   * @return tags
   */
  @NotNull @Valid 
  @JsonProperty("tags")
  public List<@Valid PartnerTagView> getTags() {
    return tags;
  }

  public void setTags(List<@Valid PartnerTagView> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PartnerView partnerView = (PartnerView) o;
    return Objects.equals(this.id, partnerView.id) &&
        Objects.equals(this.name, partnerView.name) &&
        Objects.equals(this.taxNumber, partnerView.taxNumber) &&
        Objects.equals(this.headquarters, partnerView.headquarters) &&
        Objects.equals(this.status, partnerView.status) &&
        Objects.equals(this.tags, partnerView.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, taxNumber, headquarters, status, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PartnerView {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    taxNumber: ").append(toIndentedString(taxNumber)).append("\n");
    sb.append("    headquarters: ").append(toIndentedString(headquarters)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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

