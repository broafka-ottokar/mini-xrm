package com.example.minixrm.backend.web.openapi.v1.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;
import java.io.Serializable;
import jakarta.validation.constraints.*;


import jakarta.annotation.Generated;

/**
 * ActivityView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public class ActivityView implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String subject;

  private String type;

  private String description;

  private Integer durationMinutes;

  private String personResponsible;

  private Long partnerId;

  public ActivityView() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ActivityView(Long id, String subject, String type, String description, Integer durationMinutes, String personResponsible, Long partnerId) {
    this.id = id;
    this.subject = subject;
    this.type = type;
    this.description = description;
    this.durationMinutes = durationMinutes;
    this.personResponsible = personResponsible;
    this.partnerId = partnerId;
  }

  public ActivityView id(Long id) {
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

  public ActivityView subject(String subject) {
    this.subject = subject;
    return this;
  }

  /**
   * Get subject
   * @return subject
   */
  @NotNull @Size(min = 1, max = 150) 
  @JsonProperty("subject")
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public ActivityView type(String type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @JsonProperty("type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public ActivityView description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   */
  @NotNull @Size(min = 1, max = 500000) 
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ActivityView durationMinutes(Integer durationMinutes) {
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

  public ActivityView personResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
    return this;
  }

  /**
   * Get personResponsible
   * @return personResponsible
   */
  @NotNull @Size(min = 3, max = 150) 
  @JsonProperty("personResponsible")
  public String getPersonResponsible() {
    return personResponsible;
  }

  public void setPersonResponsible(String personResponsible) {
    this.personResponsible = personResponsible;
  }

  public ActivityView partnerId(Long partnerId) {
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
    ActivityView activityView = (ActivityView) o;
    return Objects.equals(this.id, activityView.id) &&
        Objects.equals(this.subject, activityView.subject) &&
        Objects.equals(this.type, activityView.type) &&
        Objects.equals(this.description, activityView.description) &&
        Objects.equals(this.durationMinutes, activityView.durationMinutes) &&
        Objects.equals(this.personResponsible, activityView.personResponsible) &&
        Objects.equals(this.partnerId, activityView.partnerId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subject, type, description, durationMinutes, personResponsible, partnerId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActivityView {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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

