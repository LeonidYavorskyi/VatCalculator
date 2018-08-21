package com.ciklum.vatcalculator.calculatorservice.controller;

import com.ciklum.vatcalculator.calculatorservice.model.Invoice;
import com.ciklum.vatcalculator.calculatorservice.model.representation.Response;
import com.ciklum.vatcalculator.calculatorservice.service.InvoiceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    private Invoice invoice;
    private Response response;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        invoice = new Invoice(1L, 1L, 1L, 100L, 10L);
        response = new Response(1L, 200L, 20L);
    }

    @Test
    public void testCreateInvoice() throws Exception {
        when(invoiceService.create(any(Invoice.class))).thenReturn(invoice);
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put("/invoices")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(invoice)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        Invoice created = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Invoice.class);
        assertEquals(invoice, created);
    }

    @Test
    public void testCustomerBalance() throws Exception {
        when(invoiceService.calculateBalance(1L)).thenReturn(response);
        MvcResult mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get("/invoices/customers/{customerId}/balance", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andDo(MockMvcResultHandlers.print())
            .andReturn();

        Response created = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Response.class);
        assertEquals(response, created);
    }

}