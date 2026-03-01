package com.example.minixrm.backend.web.openapi.v1.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets ActivitySortFieldView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public enum ActivitySortFieldView implements Serializable {
  
  SUBJECT("subject"),
  
  TYPE("type"),
  
  DURATION_MINUTES("durationMinutes"),
  
  PERSON_RESPONSIBLE("personResponsible"),
  
  PARTNER_NAME("partner.name");

  private final String value;

  ActivitySortFieldView(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ActivitySortFieldView fromValue(String value) {
    for (ActivitySortFieldView b : ActivitySortFieldView.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

