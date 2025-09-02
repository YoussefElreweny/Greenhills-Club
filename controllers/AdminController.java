package com.example.ProjectClub.controllers; // Your package name

import com.example.ProjectClub.model.Activity;
import com.example.ProjectClub.model.Event;
import com.example.ProjectClub.model.Membership;
import com.example.ProjectClub.model.User;
import com.example.ProjectClub.service.UserSession;
import com.example.ProjectClub.store.ActivityStore;
import com.example.ProjectClub.store.EventStore;
import com.example.ProjectClub.store.MembershipStore;
import com.example.ProjectClub.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserSession userSession;
    private final UserStore userStore;
    private final EventStore eventStore;
    private final ActivityStore activityStore;
    private final MembershipStore membershipStore;


    @Autowired
    public AdminController(UserSession userSession, UserStore userStore, EventStore eventStore, ActivityStore activityStore, MembershipStore membershipStore) {
        this.userSession = userSession;
        this.userStore = userStore;
        this.eventStore = eventStore;
        this.activityStore = activityStore;
        this.membershipStore = membershipStore;
    }

    /**
     * Helper method to centralize the admin security check.
     */
    private String checkAdminAccess() {
        if (!userSession.isLoggedIn()) {
            return "redirect:/login";
        }
        Optional<User> userOpt = userStore.findByUsername(userSession.getUsername());
        if (userOpt.isEmpty() || !"ROLE_ADMIN".equals(userOpt.get().getRole())) {
            return "redirect:/member/dashboard"; // Redirect non-admins away
        }
        return null; // Access granted
    }

    /**
     * Main GET endpoint for the entire admin dashboard.
     * It loads all necessary data for the single-page view.
     */
    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        // no one will access this page unless and admin (checked with this function)
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;

        // Load data for every section of the admin dashboard
        model.addAttribute("events", eventStore.findAll());
        model.addAttribute("newEvent", new Event());

        model.addAttribute("activities", activityStore.findAll());
        model.addAttribute("newActivity", new Activity());

        model.addAttribute("memberships", membershipStore.findAll());
        model.addAttribute("newMembership", new Membership());

        model.addAttribute("users", userStore.findAll());

        return "admin";
    }

    // --- Event Management Endpoints ---

    @PostMapping("/events/add")
    public String addEvent(@ModelAttribute("newEvent") Event event) {
        // checking in each function is it an admin or not (extra security)
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;
        eventStore.save(event);
        return "redirect:/admin/dashboard#manage-events"; // Redirect back to the events section
    }

    @PostMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;
        eventStore.deleteById(id);
        return "redirect:/admin/dashboard#manage-events";
    }

    // --- Activity Management Endpoints ---

    @PostMapping("/activities/add")
    public String addActivity(@ModelAttribute("newActivity") Activity activity) {
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;
        activityStore.save(activity);
        return "redirect:/admin/dashboard#manage-activities"; // Redirect back to the activities section
    }

    @PostMapping("/activities/delete/{id}")
    public String deleteActivity(@PathVariable Long id) {
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;
        activityStore.deleteById(id);
        return "redirect:/admin/dashboard#manage-activities";
    }

    // --- Membership and User Management Endpoints would go here ---

    @PostMapping("/memberships/delete/{id}")
    public String deleteMembership(@PathVariable Long id) {
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;
        membershipStore.deleteById(id);
        return "redirect:/admin/dashboard#manage-memberships";
    }

    @PostMapping("/memberships/add")
    public String addMembership(@ModelAttribute("newMembership") Membership membership) {
        String accessCheck = checkAdminAccess();
        if (accessCheck != null) return accessCheck;

        // Note: For memberships, the 'userId' field would likely not be set from this form.
        // This form is for the PLAN, not for assigning a plan to a user.
        // You might want to create a separate "MembershipPlan" model. But for now, we'll keep it simple.

        membershipStore.save(membership);

        return "redirect:/admin/dashboard#manage-memberships";
    }

}