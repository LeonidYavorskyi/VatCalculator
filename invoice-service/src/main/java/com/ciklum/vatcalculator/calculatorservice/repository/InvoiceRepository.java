package com.ciklum.vatcalculator.calculatorservice.repository;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Query(value = "SELECT * FROM invoices i where i.customer_id = ?1", nativeQuery = true)
  List<Invoice> findByCustomerId(@Param("customer_id") Long customerId);

}
