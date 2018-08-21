package com.ciklum.vatcalculator.calculatorservice.repository;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class InvoiceRepositoryTest {

    @Autowired
    private InvoiceRepository invoiceRepository;
    private Invoice invoice;

    @Before
    public void setUp() {
        invoice = new Invoice(null, 1L, 1L, 100L, 10L);
    }

    @Test
    public void save() {
        Invoice retrieved = invoiceRepository.save(invoice);
        assertEquals(invoice.getAmount(), retrieved.getAmount());
        assertEquals(invoice.getVat(), retrieved.getVat());
        assertEquals(invoice.getCustomerId(), retrieved.getCustomerId());
    }

    @Test
    public void findByCustomerId(){
        Invoice retrieved = invoiceRepository.save(invoice);
        List<Invoice> invoices = invoiceRepository.findByCustomerId(1L);
        assertEquals(1L, invoices.size());
        assertEquals(retrieved, invoices.get(0));
    }

}