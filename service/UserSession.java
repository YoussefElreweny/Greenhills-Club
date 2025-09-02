package com.example.ProjectClub.service;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * A simple helper method to easily check if a user is currently logged in.
     * @return true if a username is set in the session, false otherwise.
     */
    public boolean isLoggedIn() {
        return this.username != null;
    }
}
