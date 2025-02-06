package com.oop.TicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vendors")
public class Vendor implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_id", nullable = false)
    private int vendorID;

    @ManyToOne
    @JoinColumn(name = "ticket_pool_id", nullable = false)
    private TicketPool ticketPool;

    @Column(name = "release_rate", nullable = false)
    private int releaseRate;

    @Column(name = "tickets_to_release", nullable = false)
    private int ticketsToRelease;

    public Vendor(int vendorID, TicketPool ticketPool, int releaseRate, int ticketsToRelease) {
        this.vendorID = vendorID;
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.ticketsToRelease = ticketsToRelease;
    }

    @Override
    public void run() {
        int releasedTickets = 0;
        try {
            while (releasedTickets < ticketsToRelease && !ticketPool.isFull()) {
                Integer ticket = ticketPool.releaseTicket(vendorID);
                if (ticket != null) {
                    releasedTickets++;
                }
                Thread.sleep(releaseRate);
            }
        } catch (InterruptedException e) {
            logger.error("Vendor {} was interrupted: {}", vendorID, e.getMessage());
            Thread.currentThread().interrupt();
        }
        logger.info("Vendor {} finished releasing {} tickets.", vendorID, releasedTickets);
    }
}
