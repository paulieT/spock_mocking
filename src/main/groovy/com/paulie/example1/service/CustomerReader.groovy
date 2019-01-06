package com.paulie.example1.service

import com.paulie.example1.entity.Customer
import com.paulie.example1.repository.EntityManager

class CustomerReader {

    private EntityManager entityManager

    String findFullName(Long id) {
        Customer customer = entityManager.find(Customer, id)

        try {
            "${customer.firstName} ${customer.lastName}"
        }
        catch (Exception ignore) {
            // Return an empty String in the case of an error
            ""
        }
    }

    void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager
    }
}
