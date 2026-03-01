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
 * CreateOrUpdateActivityRequestView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class CreateOrUpdateActivityRequestView implements Serializable {

  private static final long serialVersionUID = 1L;

  private String subject;

  private String type;

  private String description;

  private Integer durationMinutes;

  private String personResponsible;

  private Long partnerId;

  public CreateOrUpdateActivityRequestView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateOrUpdateActivityRequestView(String subject, String type, String description, Integer durationMinutes, String personResponsible, Long partnerId) {
    this.subject = subject;
    this.type = type;
    this.description = description;
    this.durationMinutes = durationMinutes;
    this.personResponsible = personResponsible;
    this.partnerId = partnerId;
  }

  public CreateOrUpdateActivityRequestView subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
   */
  @NotNull @Size(min = 0, max = 150) 
  @JsonProperty("subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public CreateOrUpdateActivityRequestView type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull @Size(min = 0, max = 50) 
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public CreateOrUpdateActivityRequestView description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull @Size(min = 0, max = 100000) 
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CreateOrUpdateActivityRequestView durationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
    return this;
  }

  /**
   * Get durationMinutes
   * minimum: 1
   * @return durationMinutes
   */
  @NotNull @Min(value = 1) 
  @JsonProperty("durationMinutes")
  public Integer getDurationMinutes() {
    return durationMinutes;
  }

  public void setDurationMinutes(Integer durationMinutes) {
    this.durationMinutes = durationMinutes;
  }

  public CreateOrUpdateActivityRequestView personResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
    return this;
  }

  /**
   * Get personResponsible
   * @return personResponsible
   */
  @NotNull @Size(min = 1, max = 150) 
  @JsonProperty("personResponsible")
  public String getPersonResponsible() {
    return personResponsible;
  }

  public void setPersonResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
  }

  public CreateOrUpdateActivityRequestView partnerId(Long partnerId) {
    this.partnerId = partnerId;
    return this;
  }

  /**
   * Get partnerId
   * minimum: 1
   * maximum: 9223372036854775807
   * @return partnerId
   */
  @NotNull @Min(value = 1L) @Max(value = 9223372036854775807L) 
  @JsonProperty("partnerId")
  public Long getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateOrUpdateActivityRequestView createOrUpdateActivityRequestView = (CreateOrUpdateActivityRequestView) o;
    return Objects.equals(this.subject, createOrUpdateActivityRequestView.subject) &&
        Objects.equals(this.type, createOrUpdateActivityRequestView.type) &&
        Objects.equals(this.description, createOrUpdateActivityRequestView.description) &&
        Objects.equals(this.durationMinutes, createOrUpdateActivityRequestView.durationMinutes) &&
        Objects.equals(this.personResponsible, createOrUpdateActivityRequestView.personResponsible) &&
        Objects.equals(this.partnerId, createOrUpdateActivityRequestView.partnerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject, type, description, durationMinutes, personResponsible, partnerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateOrUpdateActivityRequestView {\n");
    sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    durationMinutes: ").append(toIndentedString(durationMinutes)).append("\n");
    sb.append("    personResponsible: ").append(toIndentedString(personResponsible)).append("\n");
    sb.append("    partnerId: ").append(toIndentedString(partnerId)).append("\n");
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

