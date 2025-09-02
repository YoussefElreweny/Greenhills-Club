package com.example.ProjectClub.store; // Your package name

import com.example.ProjectClub.model.Activity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ActivityStore {

    // Using a Map for efficient lookup by ID.
    private final Map<Long, Activity> activities = new HashMap<>();
    private long nextId = 1L;

    /**
     * Saves an activity. Assigns a new ID if it's a new activity (id is null),
     * or updates the existing one if an ID is present.
     */
    public void save(Activity activity) {
        if (activity.getId() == null) {
            activity.setId(nextId++);
        }
        activities.put(activity.getId(), activity);
    }

    /**
     * Finds a single activity by its ID.
     */
    public Optional<Activity> findById(Long id) {
        return Optional.ofNullable(activities.get(id));
    }

    /**
     * Returns a list of all available activities.
     */
    public List<Activity> findAll() {
        return new ArrayList<>(activities.values());
    }

    /**
     * Deletes an activity by its ID.
     */
    public void deleteById(Long id) {
        activities.remove(id);
    }
}