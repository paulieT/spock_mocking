package com.paulie.example3

import com.paulie.example1.entity.Customer
import com.paulie.example2.LateInvoiceNotifier

import java.time.LocalDate

class LateInvoiceNotifierWithEventRecorder extends LateInvoiceNotifier {

    EventRecorder eventRecorder

    void notifyIfLate(Customer c) {

        if (invoiceStorage.hasOutStandingInvoice(c)) {
            emailSender.sendEmail(c)
            eventRecorder.recordEvent(new Event(timestamp: LocalDate.now(), customerName: "Paul Thomas", type: Event.Type.REMINDER_SENT))
        }
    }
}
