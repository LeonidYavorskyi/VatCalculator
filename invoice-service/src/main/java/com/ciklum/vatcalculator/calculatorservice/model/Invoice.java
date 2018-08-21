package com.ciklum.vatcalculator.calculatorservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "invoice_id", nullable = false, unique = true)
  @JsonProperty("invoice_id")
  private Long invoiceId;

  @Column(name = "customer_id", nullable = false)
  @JsonProperty("customer_id")
  private Long customerId;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private Long vat;
}
