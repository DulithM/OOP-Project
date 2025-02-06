package com.oop.TicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ticketCode;
    private boolean isRedeemed;

    @ManyToOne
    private TicketPool ticketPool;

    @ManyToOne
    private Customer customer;

}

