package com.example.minixrm.backend.web.openapi.v1.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PersonResponsibleReportItemView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class PersonResponsibleReportItemView implements Serializable {

  private static final long serialVersionUID = 1L;

  private String personResponsible;

  private Integer totalDurationMinutes;

  private Integer partnerCount;

  public PersonResponsibleReportItemView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PersonResponsibleReportItemView(String personResponsible, Integer totalDurationMinutes, Integer partnerCount) {
    this.personResponsible = personResponsible;
    this.totalDurationMinutes = totalDurationMinutes;
    this.partnerCount = partnerCount;
  }

  public PersonResponsibleReportItemView personResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
    return this;
  }

  /**
   * Get personResponsible
   * @return personResponsible
   */
  @NotNull @Size(min = 0, max = 150) 
  @JsonProperty("personResponsible")
  public String getPersonResponsible() {
    return personResponsible;
  }

  public void setPersonResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
  }

  public PersonResponsibleReportItemView totalDurationMinutes(Integer totalDurationMinutes) {
    this.totalDurationMinutes = totalDurationMinutes;
    return this;
  }

  /**
   * Get totalDurationMinutes
   * minimum: 0
   * @return totalDurationMinutes
   */
  @NotNull @Min(value = 0) 
  @JsonProperty("totalDurationMinutes")
  public Integer getTotalDurationMinutes() {
    return totalDurationMinutes;
  }

  public void setTotalDurationMinutes(Integer totalDurationMinutes) {
    this.totalDurationMinutes = totalDurationMinutes;
  }

  public PersonResponsibleReportItemView partnerCount(Integer partnerCount) {
    this.partnerCount = partnerCount;
    return this;
  }

  /**
   * Get partnerCount
   * minimum: 0
   * @return partnerCount
   */
  @NotNull @Min(value = 0) 
  @JsonProperty("partnerCount")
  public Integer getPartnerCount() {
    return partnerCount;
  }

  public void setPartnerCount(Integer partnerCount) {
    this.partnerCount = partnerCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonResponsibleReportItemView personResponsibleReportItemView = (PersonResponsibleReportItemView) o;
    return Objects.equals(this.personResponsible, personResponsibleReportItemView.personResponsible) &&
        Objects.equals(this.totalDurationMinutes, personResponsibleReportItemView.totalDurationMinutes) &&
        Objects.equals(this.partnerCount, personResponsibleReportItemView.partnerCount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personResponsible, totalDurationMinutes, partnerCount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonResponsibleReportItemView {\n");
    sb.append("    personResponsible: ").append(toIndentedString(personResponsible)).append("\n");
    sb.append("    totalDurationMinutes: ").append(toIndentedString(totalDurationMinutes)).append("\n");
    sb.append("    partnerCount: ").append(toIndentedString(partnerCount)).append("\n");
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

