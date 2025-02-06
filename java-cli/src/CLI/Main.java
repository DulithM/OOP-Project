package CLI;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Configuration configuration = null;
        String loadConfig;

        // Ask the user if they want to load configuration from a file
        while (true) {
            try {
                System.out.print("Load configuration from file? (y/n): ");
                loadConfig = scanner.nextLine().trim().toLowerCase();
                if (loadConfig.equals("y") || loadConfig.equals("n")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                scanner.next();
            }
        }

        // Ask user for vendor count and customer count
        int vendorCount = getValidPositiveNumber(scanner, "Enter the number of vendors: ");
        int customerCount = getValidPositiveNumber(scanner, "Enter the number of customers: ");

        // Load or set up configuration
        if (loadConfig.equalsIgnoreCase("y")) {
            configuration = Configuration.loadFromJsonFile("config.json");
            if (configuration == null) {
                System.out.println("Failed to load configuration from file. Please try again.");
                return;
            }
        } else {
            configuration = new Configuration();

            configuration.setTotalTickets(getValidPositiveNumber(scanner, "Total number of tickets: "));
            configuration.setTicketReleaseRate(getValidPositiveNumber(scanner, "Ticket release rate (ms): "));
            configuration.setCustomerRetrievalRate(getValidPositiveNumber(scanner, "Customer retrieval rate (ms): "));
            configuration.setMaxTicketCapacity(getValidPositiveNumber(scanner, "Maximum ticket capacity: "));
        }

        // Save the configuration
        configuration.saveToJsonFile("config.json");
        configuration.saveToTxtFile("config.txt");

        // Initialize the ticket pool
        TicketPool ticketPool = new TicketPool(configuration.getMaxTicketCapacity());

        // Calculate tickets per vendor
        int ticketsPerVendor = configuration.getTotalTickets() / vendorCount;

        // Create thread pool
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create and execute Vendor threads
        for (int i = 1; i <= vendorCount; i++) {
            Vendor vendor = new Vendor(i, ticketPool, configuration.getTicketReleaseRate(), ticketsPerVendor);
            executor.execute(vendor);
        }

        // Create and execute Customer threads
        for (int i = 1; i <= customerCount; i++) {
            Customer customer = new Customer(i, ticketPool, configuration.getCustomerRetrievalRate());
            executor.execute(customer);
        }

        // Shutdown executor
        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("Execution interrupted.");
        }

        System.out.println("All tasks completed.");
        System.exit(0);
    }

    // Helper method to get a valid positive number from the user
    private static int getValidPositiveNumber(Scanner scanner, String prompt) {
        int number;
        while (true) {
            try {
                System.out.print(prompt);
                number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
        }
    }
}
