package com.paulie.test

import com.paulie.example1.entity.Customer
import com.paulie.example1.repository.EntityManager
import com.paulie.example1.service.CustomerReader
import spock.lang.Specification

class CustomerReaderSpec extends Specification {

    private CustomerReader customerReader
    private EntityManager entityManager

    void setup() {
        customerReader = new CustomerReader()
        entityManager = Stub(EntityManager)

        customerReader.setEntityManager(entityManager)
    }

    void "customer full name is formed from first name and last name"() {

        given: "a customer with example name values"
        Customer customer = new Customer(firstName: "Susan", lastName: "Thomas")

        and: "an entity manager that always returns this customer"
        entityManager.find(Customer, 1L) >> customer

        when: "we ask for ask for the full name of the customer"
        String fullName = customerReader.findFullName(1L)

        then: "we get both the first and last name"
        "Susan Thomas" == fullName
    }

    void "customer is not in the database"() {

        given: "the database has no record of the customer"
        entityManager.find(Customer, 1L) >> null

        when: "we ask for ask for the full name of the customer"
        String fullName = customerReader.findFullName(1L)

        then: "an empty string is returned"
        "" == fullName
    }
}
