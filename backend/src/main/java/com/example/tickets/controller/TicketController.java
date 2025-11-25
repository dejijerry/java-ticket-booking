package com.example.tickets.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tickets.dto.BookingResponse;
import com.example.tickets.service.TicketService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) { this.ticketService = ticketService; }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvent(@PathVariable Long id) {
        return ticketService.findEvent(id)
            .map(e -> ResponseEntity.ok(e))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<BookingResponse> book(@PathVariable Long id, @RequestParam int count) {
        try {
            boolean ok = ticketService.bookTickets(id, count);
            if (ok) return ResponseEntity.ok(new BookingResponse(true, "Booked " + count + " tickets"));
            else return ResponseEntity.badRequest().body(new BookingResponse(false, "Not enough tickets available"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new BookingResponse(false, ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(new BookingResponse(false, "Internal error"));
        }
    }
}
