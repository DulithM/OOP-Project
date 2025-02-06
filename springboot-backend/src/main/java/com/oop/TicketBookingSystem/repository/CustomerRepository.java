package com.oop.TicketBookingSystem.repository;

import com.oop.TicketBookingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

