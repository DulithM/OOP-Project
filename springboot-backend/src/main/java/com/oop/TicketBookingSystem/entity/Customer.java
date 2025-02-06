package com.oop.TicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private int customerID;

    @ManyToOne
    @JoinColumn(name = "ticket_pool_id", nullable = false)
    private TicketPool ticketPool;

    @Column(name = "retrieval_rate", nullable = false)
    private int retrievalRate;

    public Customer(int customerID, TicketPool ticketPool, int retrievalRate) {
        this.customerID = customerID;
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public void run() {
        try {
            while (!ticketPool.isFull()) {
                Integer ticket = ticketPool.retrieveTicket(customerID);
                if (ticket == null) {
                    logger.info("Customer {} could not retrieve a ticket as the pool is empty.", customerID);
                    break;
                }
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e) {
            logger.error("Customer {} was interrupted: {}", customerID, e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Customer {} finished retrieving tickets.", customerID);
    }
}
