package com.mycompany.invoise.invoiseweb.controller;

import com.mycompany.invoise.core.entity.Address;
import com.mycompany.invoise.core.entity.Customer;
import com.mycompany.invoise.core.entity.Invoice;
import com.mycompany.invoise.core.service.InvoiceServiceInterface;
import com.mycompany.invoise.invoiseweb.form.InvoiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/invoice")
public class InvoiceControllerWeb {

    @Autowired
    private InvoiceServiceInterface invoiceService;

    @PostMapping("/create")
    public String createInvoice(@Valid @ModelAttribute InvoiceForm invoiceForm, BindingResult results) {
        if(results.hasErrors()) {
            return "invoice-create-form";
        }
        Invoice invoice = new Invoice();
        Customer customer = new Customer(invoiceForm.getCustomerName());
        invoice.setCustomer(customer);
        Address address = new Address(invoiceForm.getStreetName(), invoiceForm.getStreetNumber(), invoiceForm.getCity(), invoiceForm.getZipCode(), invoiceForm.getCountry());
        customer.setAddress(address);
        // invoice.setOrderNumber(invoiceForm.getOrderNumber());
        invoiceService.createInvoice(invoice);
        return "invoice-created";
    }

    public InvoiceServiceInterface getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(InvoiceServiceInterface invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/home")
    public String displayHome(Model model) {
        System.out.println("La methode displayHome a été invoquée");
        model.addAttribute("invoices", invoiceService.getInvoiceList());
        return "invoice-home";
    }

    /*
    @GetMapping("/{id}")
    public String displayInvoice(@PathVariable("id") String number, Model model) {
        System.out.println("La methode displayInvoice a été invoquée");
        model.addAttribute("invoice", invoiceService.getInvoiceByNumber(number));
        //ist<Invoice> invoices = invoiceService.getInvoiceList();
        return "invoice-details";
    }
    */

    @GetMapping("/create-form")
    public String displayInvoiceCreateForm(@ModelAttribute InvoiceForm invoiceForm) {
        return "invoice-create-form";
    }
}
