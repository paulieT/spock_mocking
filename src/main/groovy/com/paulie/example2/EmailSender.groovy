package com.paulie.example2

import com.paulie.example1.entity.Customer

class EmailSender {
    void sendEmail(Customer customer) {
        println "Sending email because ${customer.lastName}, ${customer.firstName} is late on payment"
    }
}
