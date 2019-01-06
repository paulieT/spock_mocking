package com.paulie.test

import com.paulie.example1.entity.Customer
import com.paulie.example2.EmailSender
import com.paulie.example2.InvoiceStorage
import com.paulie.example3.Event
import com.paulie.example3.EventRecorder
import com.paulie.example3.LateInvoiceNotifierWithEventRecorder
import spock.lang.Specification

class EventCheckSpec extends Specification {

    // ------------------
    // Class to be tested
    // ------------------
    private LateInvoiceNotifierWithEventRecorder lateInvoiceNotifierWithEventRecorder

    // -----------------------------
    // Dependencies (will be mocked)
    // -----------------------------
    private EmailSender emailSender
    private EventRecorder eventRecorder
    private InvoiceStorage invoiceStorage

    // ---------
    // Test Data
    // ---------
    Customer sampleCustomer

    void setup() {
        emailSender    = Mock(EmailSender)
        eventRecorder  = Mock(EventRecorder)
        invoiceStorage = Mock(InvoiceStorage)

        lateInvoiceNotifierWithEventRecorder = new LateInvoiceNotifierWithEventRecorder(emailSender: emailSender,
                                                                                        eventRecorder: eventRecorder,
                                                                                        invoiceStorage: invoiceStorage)

        sampleCustomer = new Customer(firstName: "Paul", lastName: "Thomas")
    }

    void "email about late invoice should contain customer details"() {

        given: "A customer with a late invoice"
        invoiceStorage.hasOutStandingInvoice(sampleCustomer) >> true

        when: "We chack that an email should be sent"
        lateInvoiceNotifierWithEventRecorder.notifyIfLate(sampleCustomer)

        then: "Customer is indeed emailed"
        1 * emailSender.sendEmail(sampleCustomer)

        and: "the event is indeed recorded with the correct details"
        1 * eventRecorder.recordEvent({ Event event ->

            event.timestamp    != null                     &&
            event.type         == Event.Type.REMINDER_SENT &&
            event.customerName == "${sampleCustomer.firstName} ${sampleCustomer.lastName}"

        })
    }
}
