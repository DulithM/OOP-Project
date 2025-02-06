package com.oop.TicketBookingSystem.repository;

import com.oop.TicketBookingSystem.entity.TicketPool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPoolRepository extends JpaRepository<TicketPool, Long> {
}

