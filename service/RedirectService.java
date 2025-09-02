package com.example.ProjectClub.service;

import com.example.ProjectClub.model.User;
import com.example.ProjectClub.service.UserSession;
import com.example.ProjectClub.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RedirectService {

    private final UserStore userStore;
    private final UserSession userSession;

    @Autowired
    public RedirectService(UserStore userStore, UserSession userSession) {
        this.userStore = userStore;
        this.userSession = userSession;
    }

    /**
     * Determines the correct dashboard URL for the currently logged-in user.
     * @return The redirect URL string.
     */
    public String getRedirectUrlAfterLogin() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/login?error"; // Should not happen, but a safe fallback
        }

        Optional<User> userOpt = userStore.findByUsername(userSession.getUsername());

        // Check the role and return the appropriate path
        if (userOpt.isPresent() && "ROLE_ADMIN".equals(userOpt.get().getRole())) {
            return "redirect:/admin/dashboard";
        } else {
            // Default for everyone else (including normal users)
            return "redirect:/member/dashboard";
        }
    }
}