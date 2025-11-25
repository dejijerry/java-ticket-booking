package com.example.tickets;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.tickets.model.Event;
import com.example.tickets.repository.EventRepository;

@SpringBootApplication
public class TicketBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketBookingApplication.class, args);
    }

    @Bean
    CommandLineRunner init(EventRepository repo) {
        return args -> {
            repo.save(new Event("Rock Concert", 100));
            repo.save(new Event("Tech Conference", 50));
            repo.save(new Event("Comedy Night", 30));
        };
    }
}
