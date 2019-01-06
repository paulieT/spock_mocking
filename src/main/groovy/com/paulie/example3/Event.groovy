package com.paulie.example3

import java.time.LocalDate

class Event {

    enum Type {
        REMINDER_SENT, REGISTRATION, INVOICE_ISSUED, PAYMENT, SETTLEMENT
    }
    Type type
    String customerName
    LocalDate timestamp
}
