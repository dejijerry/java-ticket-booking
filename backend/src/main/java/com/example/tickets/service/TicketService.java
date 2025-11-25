package com.example.tickets.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tickets.model.Event;
import com.example.tickets.repository.EventRepository;

@Service
public class TicketService {

    private final EventRepository eventRepository;

    public TicketService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> findEvent(Long id) {
        return eventRepository.findById(id);
    }

    @Transactional
    public synchronized boolean bookTickets(Long eventId, int count) {
        Event event = eventRepository.findByIdForUpdate(eventId)
            .orElseThrow(() -> new IllegalArgumentException("Event not found: " + eventId));

        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than 0");
        }

        if (event.getAvailableTickets() < count) {
            return false;
        }

        event.setAvailableTickets(event.getAvailableTickets() - count);
        eventRepository.save(event);
        return true;
    }
}
