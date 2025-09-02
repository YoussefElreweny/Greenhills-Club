package com.example.ProjectClub.controllers;

import com.example.ProjectClub.service.UserSession;
import com.example.ProjectClub.store.ActivityStore;
import com.example.ProjectClub.store.EventStore;
import com.example.ProjectClub.store.MembershipStore;
import com.example.ProjectClub.store.UserStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final UserSession userSession;
    private final UserStore userStore;
    private final EventStore eventStore;
    private final ActivityStore activityStore;
    private final MembershipStore membershipStore;

    public MemberController(UserSession userSession, UserStore userStore, EventStore eventStore, ActivityStore activityStore, MembershipStore membershipStore) {
        this.userSession = userSession;
        this.userStore = userStore;
        this.eventStore = eventStore;
        this.activityStore = activityStore;
        this.membershipStore = membershipStore;
    }


    // in this funcion i should have used a service class to seperate this logic
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // --- MANUAL SECURITY CHECK ---
        if (!userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        String username = userSession.getUsername();
        model.addAttribute("username", username);

        // Try to find the user in the store to get their ID for the membership lookup
        userStore.findByUsername(username).ifPresent(user -> {
            // Find the membership for this user ID. For this simple app, we'll just get the first one.
            membershipStore.findByUserId((long) user.getId()).stream().findFirst()
                    .ifPresent(membership -> model.addAttribute("membership", membership));
        });

        return "member-dashboard";
    }


    @GetMapping("/events")
    public String showEvents(Model model) {

        if (!userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        model.addAttribute("username", userSession.getUsername());

        model.addAttribute("events", eventStore.findAll());
        return "events";
    }


    @GetMapping("/activities")
    public String showActivities(Model model) {
        // --- MANUAL SECURITY CHECK ---
        if (!userSession.isLoggedIn()) {
            return "redirect:/login";
        }

        // Add username to the model for the header
        model.addAttribute("username", userSession.getUsername());


        model.addAttribute("activities", activityStore.findAll());
        return "activities";
    }
}
