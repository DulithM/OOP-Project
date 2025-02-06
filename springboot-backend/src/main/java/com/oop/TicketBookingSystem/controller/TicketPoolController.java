package com.oop.TicketBookingSystem.controller;

import com.oop.TicketBookingSystem.entity.Customer;
import com.oop.TicketBookingSystem.entity.TicketPool;
import com.oop.TicketBookingSystem.entity.Vendor;
import com.oop.TicketBookingSystem.repository.TicketPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/ticketpools")
@CrossOrigin(origins = "http://localhost:4200")
public class TicketPoolController {

    @Autowired
    private TicketPoolRepository ticketPoolRepository;

    // Create a new TicketPool
    @PostMapping
    public ResponseEntity<?> createTicketPool(@Valid @RequestBody TicketPool ticketPool) {
        try {
            TicketPool savedTicketPool = ticketPoolRepository.save(ticketPool);

            displayTicketPoolDetails(savedTicketPool);
            commandLineDisplay(savedTicketPool);

            return ResponseEntity.ok(savedTicketPool);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create TicketPool: " + e.getMessage());
        }
    }

    // Get all TicketPools
    @GetMapping
    public ResponseEntity<List<TicketPool>> getAllTicketPools() {
        List<TicketPool> ticketPools = ticketPoolRepository.findAll();
        return ResponseEntity.ok(ticketPools);
    }

    private void displayTicketPoolDetails(TicketPool ticketPool) {
        System.out.println(" ");
        System.out.println("==============================================================");
        System.out.println("|-----------------Received TicketPool Details----------------|");
        System.out.println("==============================================================");
        System.out.println("              Ticket Pool ID          : " + ticketPool.getId());
        System.out.println("              Vendor Count            : " + ticketPool.getVendorCount());
        System.out.println("              Customer Count          : " + ticketPool.getCustomerCount());
        System.out.println("==============================================================");
        System.out.println("              Total Number of Tickets : " + ticketPool.getTotalTickets());
        System.out.println("              Ticket Release Rate     : " + ticketPool.getReleaseRate());
        System.out.println("              Ticket Retrieval Rate   : " + ticketPool.getRetrievalRate());
        System.out.println("              Maximum Ticket Capacity : " + ticketPool.getMaxTickets());
    }

    private void commandLineDisplay(TicketPool ticketPool) {

        int vendorCount = ticketPool.getVendorCount();
        int customerCount = ticketPool.getCustomerCount();
        int totalTickets = ticketPool.getTotalTickets();
        int releaseRate = ticketPool.getReleaseRate();
        int retrievalRate = ticketPool.getRetrievalRate();
        int maxTickets = ticketPool.getMaxTickets();

        int ticketsPerVendor = totalTickets / vendorCount;

        ExecutorService executor = Executors.newCachedThreadPool();

        System.out.println("==============================================================");
        System.out.println("|------------------------Processing--------------------------|");
        System.out.println("==============================================================");

        // Create and execute Vendor threads
        for (int i = 1; i <= vendorCount; i++) {
            Vendor vendor = new Vendor(i, ticketPool, releaseRate, ticketsPerVendor);
            executor.execute(vendor);
        }

        // Create and execute Customer threads
        for (int i = 1; i <= customerCount; i++) {
            Customer customer = new Customer(i, ticketPool, retrievalRate);
            executor.execute(customer);
        }

        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(45, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted.");
        }


        System.out.println("==============================================================");
        System.out.println("|---------------------All tasks completed--------------------|");
        System.out.println("==============================================================");

    }
}