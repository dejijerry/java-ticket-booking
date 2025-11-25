package com.example.tickets;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.tickets.model.Event;
import com.example.tickets.repository.EventRepository;
import com.example.tickets.service.TicketService;

@SpringBootTest
public class ConcurrentBookingTests {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testConcurrentBookings_noOverbook() throws Exception {
        Event e = eventRepository.save(new Event("Stress Test", 10));
        int threads = 20;
        ExecutorService exec = Executors.newFixedThreadPool(threads);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            exec.submit(() -> {
                try {
                    startLatch.await();
                    try {
                        ticketService.bookTickets(e.getId(), 1);
                    } catch (Exception ex) {}
                } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
                finally { doneLatch.countDown(); }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        Event updated = eventRepository.findById(e.getId()).orElseThrow();
        assertThat(updated.getAvailableTickets()).isEqualTo(0);
        exec.shutdownNow();
    }
}
