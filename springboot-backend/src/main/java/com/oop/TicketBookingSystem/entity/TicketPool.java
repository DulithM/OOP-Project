package com.oop.TicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ticket_pool")
public class TicketPool {

    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "ticket_pool_tickets", joinColumns = @JoinColumn(name = "ticket_pool_id"))
    @Column(name = "ticket_number")
    private List<Integer> tickets = new ArrayList<>();

    @NotNull(message = "Total Vendors cannot be null")
    @Min(value = 1, message = "Total Vendors must be at least 1")
    private Integer vendorCount;

    @NotNull(message = "Total Customers cannot be null")
    @Min(value = 1, message = "Total Customers must be at least 1")
    private Integer customerCount;

    @NotNull(message = "Total Tickets cannot be null")
    @Min(value = 1, message = "Total Tickets must be at least 1")
    private Integer totalTickets;

    @NotNull(message = "Release Rate cannot be null")
    @Min(value = 1, message = "Release Rate must be at least 1")
    private Integer releaseRate;

    @NotNull(message = "Retrieval Rate cannot be null")
    @Min(value = 1, message = "Retrieval Rate must be at least 1")
    private Integer retrievalRate;

    @NotNull(message = "Max Tickets cannot be null")
    @Min(value = 1, message = "Max Tickets must be at least 1")
    private Integer maxTickets;

    public TicketPool() {
    }

    public TicketPool(int maxTickets) {
        this.tickets = new ArrayList<>();
        this.maxTickets = maxTickets;
    }

    // Check if the pool is full
    public synchronized boolean isFull() {
        return tickets.size() >= maxTickets;
    }

    // Retrieve a ticket from the pool
    public synchronized Integer retrieveTicket(int customerID) {
        while (tickets.isEmpty()) {
            try {
                logger.info("Customer {} is waiting for tickets...", customerID);
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Integer ticket = tickets.remove(0);
        logger.info("Customer {} retrieved ticket number: {}", customerID, ticket);
        notifyAll();
        return ticket;
    }

    // Release a ticket into the pool
    public synchronized Integer releaseTicket(int vendorID) {
        if (isFull()) {
            logger.info("Vendor {} is waiting for space to release tickets...", vendorID);
            return null;
        }
        int ticketNumber = tickets.size() + 1;
        tickets.add(ticketNumber);
        logger.info("Vendor {} released ticket number: {}", vendorID, ticketNumber);
        notifyAll();
        return ticketNumber;
    }
}
