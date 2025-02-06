package CLI;

public class Customer implements Runnable{
    private final int customerID;
    private final TicketPool ticketPool;
    private final int retrievalRate;

    public Customer(int customerID, TicketPool ticketPool, int retrievalRate) {
        this.customerID = customerID;
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
    }

    @Override
    public  void run(){
        try{
            while (!ticketPool.isFull()){
                Integer ticket = ticketPool.retrieveTicket(customerID);
                if (ticket == null){
                    break;
                }
                Thread.sleep(retrievalRate);
            }
        } catch (InterruptedException e){
            System.out.println("Customer " + customerID + " was interrupted.");
        }
        System.out.println("Customer " + customerID + " finished retrieving tickets.");
    }
}
