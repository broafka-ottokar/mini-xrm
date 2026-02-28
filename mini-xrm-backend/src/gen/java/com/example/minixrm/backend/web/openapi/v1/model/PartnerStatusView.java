package com.example.minixrm.backend.web.openapi.v1.model;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets PartnerStatusView
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.19.0")
public enum PartnerStatusView implements Serializable {
  
  ACTIVE("ACTIVE"),
  
  INACTIVE("INACTIVE");

  private final String value;

  PartnerStatusView(String value) {
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
  public static PartnerStatusView fromValue(String value) {
    for (PartnerStatusView b : PartnerStatusView.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

