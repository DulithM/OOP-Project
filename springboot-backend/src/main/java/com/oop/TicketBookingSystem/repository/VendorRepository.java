package com.oop.TicketBookingSystem.repository;

import com.oop.TicketBookingSystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}

