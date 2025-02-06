package com.oop.TicketBookingSystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/system")
@CrossOrigin(origins = "http://localhost:4200")
public class SystemController {

    private boolean isServiceRunning = false;
    private Process serviceProcess;

    // Start the service
    @PostMapping("/start")
    public ResponseEntity<String> startService() {
        if (isServiceRunning) {
            return ResponseEntity.badRequest().body("Service is already running.");
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("java", "-jar", "/path/to/your/spring-boot-application.jar");
            serviceProcess = processBuilder.start();

            isServiceRunning = true;

            System.out.println("==============================================================");
            System.out.println("|------------------------System Start------------------------|");
            System.out.println("==============================================================");
            return ResponseEntity.ok("Service started successfully.");
        } catch (IOException e) {
            System.err.println("Error starting the service: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to start the service: " + e.getMessage());
        }
    }

    // Stop the service
    @PostMapping("/stop")
    public ResponseEntity<String> stopService() {
        if (!isServiceRunning) {
            return ResponseEntity.badRequest().body("Service is not running.");
        }

        try {
            if (serviceProcess != null) {
                serviceProcess.destroy();
                isServiceRunning = false;
                System.out.println("|-----------------------System Stopping----------------------|");
                System.out.println("==============================================================");
                System.exit(0);
                return ResponseEntity.ok("Service stopped successfully.");
            } else {
                return ResponseEntity.status(500).body("Service process is null. Unable to stop.");
            }
        } catch (Exception e) {
            System.err.println("Error stopping the service: " + e.getMessage());
            return ResponseEntity.status(500).body("Failed to stop the service: " + e.getMessage());
        }
    }

    // Get the status of the service
    @GetMapping("/status")
    public ResponseEntity<String> getServiceStatus() {
        return ResponseEntity.ok(isServiceRunning ? "Running" : "Stopped");
    }
}
