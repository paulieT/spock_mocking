package com.paulie.test

import com.paulie.example1.entity.Customer
import com.paulie.example2.EmailSender
import com.paulie.example2.InvoiceStorage
import com.paulie.example2.LateInvoiceNotifier
import spock.lang.Specification

class LateInvoiceNotifierSpec extends Specification {

    // ------------------
    // Class to be tested
    // ------------------
    private LateInvoiceNotifier lateInvoiceNotifier

    // -----------------------------
    // Dependencies (will be mocked)
    // -----------------------------
    private EmailSender emailSender
    private InvoiceStorage invoiceStorage

    // ---------
    // Test date
    // ---------
    private Customer customer

    void setup() {
        emailSender         = Mock(EmailSender)
        invoiceStorage      = Mock(InvoiceStorage)
        lateInvoiceNotifier = new LateInvoiceNotifier(emailSender: emailSender, invoiceStorage: invoiceStorage)
        customer            = new Customer(firstName: "Paul", lastName: "Thomas")
    }

    void "a late invoice should trigger an email"() {

        given: "A customer with a late invoice"
        invoiceStorage.hasOutStandingInvoice(customer) >> true

        when: "we check that an email should be sent"
        lateInvoiceNotifier.notifyIfLate(customer)

        then: "the customer is indeed emailed"
        1 * emailSender.sendEmail(customer)
    }

    void "no late invoices"() {
        given: "a customer with good standing"
        invoiceStorage.hasOutStandingInvoice(customer) >> false

        when: "we check if an email should be sent"
        lateInvoiceNotifier.notifyIfLate(customer)

        then: "an email is never sent out"
        0 * emailSender.sendEmail(customer)
    }
}
