package com.ciklum.vatcalculator.calculatorservice.model.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {
  @JsonProperty("customer_id")
  private Long customerId;
  @JsonProperty("total_amount")
  private Long totalAmount;
  @JsonProperty("total_vat")
  private Long totalVat;
}
