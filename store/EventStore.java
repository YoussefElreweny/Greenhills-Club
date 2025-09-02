package com.example.ProjectClub.store;

import com.example.ProjectClub.model.Event;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventStore {
    private final Map<Long, Event> events = new HashMap<>();
    private long nextId = 1L;


    /**
     * Saves an event. If it's a new event (id is null), it generates a new ID.
     * If it's an existing event (id is not null), it updates it.
     */
    public void save(Event event) {
        if (event.getId() == null) {
            event.setId(nextId++);
        }
        events.put(event.getId(), event);
    }

    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }

    public List<Event> findAll() {
        return new ArrayList<>(events.values());
    }

    public void deleteById(Long id) {
        events.remove(id);
    }
}
