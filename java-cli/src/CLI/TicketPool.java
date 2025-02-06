package CLI;

import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final Queue<Integer> tickets;
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.tickets = new LinkedList<>();
        this.maxCapacity = maxCapacity;
    }

    // Check if the pool is full
    public synchronized boolean isFull() {
        return tickets.size() >= maxCapacity;
    }

    // Retrieve a ticket from the pool
    public synchronized Integer retrieveTicket(int customerID) {
        while (tickets.isEmpty()) {
            try {
                System.out.println("Customer " + customerID + " is waiting for tickets...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        Integer ticketNumber = tickets.poll();
        String ticketCode = "Ticket V" + customerID + "#" + ticketNumber;
        System.out.println("Customer " + customerID + " retrieved ticket : " + ticketCode);
        notifyAll();
        return ticketNumber;
    }

    // Release a ticket into the pool
    public synchronized Integer releaseTicket(int vendorID) {
        if (isFull()) {
            System.out.println("Vendor " + vendorID + " is waiting for space to release tickets...");
            return null;
        }
        int ticketNumber = tickets.size() + 1;
        tickets.add(ticketNumber);

        String ticketCode = "Ticket V" + vendorID + "#" + ticketNumber;
        System.out.println("Vendor " + vendorID + " released ticket : " + ticketCode );
        notifyAll();
        return ticketNumber;
    }
}
