package com.paulie.example2

import com.paulie.example1.entity.Customer

class LateInvoiceNotifier {

    EmailSender emailSender
    InvoiceStorage invoiceStorage

    void notifyIfLate(Customer c) {

        if (invoiceStorage.hasOutStandingInvoice(c)) {
            emailSender.sendEmail(c)
        }
    }
}
