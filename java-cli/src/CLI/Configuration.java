package CLI;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Configuration {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void saveToJsonFile(String filename) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            System.out.println("Failed to save configuration to file.");
        }
    }

    public void saveToTxtFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Total number of tickets: " + totalTickets + "\n");
            writer.write("Ticket release rate(per second): " + ticketReleaseRate + "\n");
            writer.write("Customer retrieval rate(per second): " + customerRetrievalRate + "\n");
            writer.write("Maximum ticket capacity: " + maxTicketCapacity + "\n");
        } catch (IOException e) {
            System.out.println("Failed to save configuration to file.");
        }
    }

    public static Configuration loadFromJsonFile(String filename) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filename)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Failed to load configuration from file.");
            return null;
        }
    }
}

