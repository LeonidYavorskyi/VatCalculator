package com.ciklum.vatcalculator.calculatorservice.controller;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;
import com.ciklum.vatcalculator.calculatorservice.model.representation.Response;
import com.ciklum.vatcalculator.calculatorservice.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PutMapping
    public Invoice create(@RequestBody Invoice invoice) {
        return invoiceService.create(invoice);
    }

    @GetMapping("customers/{customerId}/balance")
    public Response customerBalance(@PathVariable Long customerId) {
        return invoiceService.calculateBalance(customerId);
    }

}
