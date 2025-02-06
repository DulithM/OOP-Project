package com.oop.TicketBookingSystem.repository;

import com.oop.TicketBookingSystem.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

