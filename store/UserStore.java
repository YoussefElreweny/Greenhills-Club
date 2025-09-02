package com.example.ProjectClub.store; // Let's use a 'store' package

import com.example.ProjectClub.model.User;
import org.springframework.stereotype.Repository; // Using @Repository is more specific and better

import java.util.*;

@Repository
public class UserStore {


    private final Map<String, User> users = new HashMap<>();
    private long nextId = 1L;

    public void save(User user) {
        if (user.getId() == 0) { // Check if it's a new user (default int is 0)
            user.setId(nextId++);
        }
        users.put(user.getUsername(), user);
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }


    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

}