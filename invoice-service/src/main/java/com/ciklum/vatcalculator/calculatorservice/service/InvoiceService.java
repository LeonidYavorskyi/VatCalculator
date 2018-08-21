package com.ciklum.vatcalculator.calculatorservice.service;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;
import com.ciklum.vatcalculator.calculatorservice.model.representation.Response;
import com.ciklum.vatcalculator.calculatorservice.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ToLongFunction;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice create(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public Response calculateBalance(Long customerId) {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        return new Response(
            customerId,
            calculateTotal(invoices, Invoice::getAmount),
            calculateTotal(invoices, Invoice::getVat)
        );
    }

    private Long calculateTotal(List<Invoice> invoices, ToLongFunction<Invoice> function) {
        return invoices.stream().mapToLong(function).sum();
    }

}
