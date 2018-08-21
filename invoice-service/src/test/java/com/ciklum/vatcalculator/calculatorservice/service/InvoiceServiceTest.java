package com.ciklum.vatcalculator.calculatorservice.service;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;
import com.ciklum.vatcalculator.calculatorservice.model.representation.Response;
import com.ciklum.vatcalculator.calculatorservice.repository.InvoiceRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    private Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice(1L, 1L, 1L, 100L, 10L);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);
        when(invoiceRepository.findByCustomerId(1L)).thenReturn(Collections.singletonList(invoice));
        when(invoiceRepository.findByCustomerId(2L)).thenReturn(Arrays.asList(invoice, invoice));
    }

    @Test
    public void create() {
        Invoice created = invoiceService.create(invoice);
        assertEquals(invoice, created);
    }

    @Test
    public void calculateBalance() {
        Response response = invoiceService.calculateBalance(1L);
        assertEquals(1L, response.getCustomerId().longValue());
        assertEquals(invoice.getVat(), response.getTotalVat());
        assertEquals(invoice.getAmount(), response.getTotalAmount());
    }

    @Test
    public void calculateBalanceForMultipleInvoices() {
        Response response = invoiceService.calculateBalance(2L);
        assertEquals(20L, response.getTotalVat().longValue());
        assertEquals(200L, response.getTotalAmount().longValue());
    }
}