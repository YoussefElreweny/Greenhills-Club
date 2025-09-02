package com.example.ProjectClub.store; // Your package name

import com.example.ProjectClub.model.Membership;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MembershipStore {

    // Using a Map for efficient lookup by the membership ID.
    private final Map<Long, Membership> memberships = new HashMap<>();
    private long nextId = 1L;


    /**
     * Saves a membership. Assigns a new ID if it's new, or updates if it exists.
     */
    public void save(Membership membership) {
        if (membership.getId() == null) {
            membership.setId(nextId++);
        }
        memberships.put(membership.getId(), membership);
    }

    /**
     * Finds a single membership by its own unique ID.
     */
    public Optional<Membership> findById(Long id) {
        return Optional.ofNullable(memberships.get(id));
    }


    /**
     * A custom and very useful method to find all memberships belonging to a specific user.
     */
    // Replace the old findByUserId method in MembershipStore.java with this one
    public List<Membership> findByUserId(Long userId) {
        if (userId == null) {
            return new ArrayList<>(); // Return an empty list if we're asked to find a null user
        }

        return memberships.values().stream()
                .filter(membership -> membership.getUserId() != null && membership.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of all memberships in the system.
     */
    public List<Membership> findAll() {
        return new ArrayList<>(memberships.values());
    }

    /**
     * Deletes a membership by its ID.
     */
    public void deleteById(Long id) {
        memberships.remove(id);
    }
}