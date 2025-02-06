package CLI;

public class Vendor implements Runnable {
    private final int vendorID;
    private final TicketPool ticketPool;
    private final int releaseRate;      // Rate of releasing tickets (in milliseconds)
    private final int ticketsToRelease; // Total tickets this vendor is allowed to release

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
            System.out.println("Vendor " + vendorID + " was interrupted.");
        }
        System.out.println("Vendor " + vendorID + " finished releasing " + releasedTickets + " tickets.");
    }
}
