package com.example.ProjectClub.controllers;

import com.example.ProjectClub.model.User;
import com.example.ProjectClub.service.LoginProcessor;
import com.example.ProjectClub.service.RedirectService;
import com.example.ProjectClub.service.UserSession;
import com.example.ProjectClub.store.UserStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    // we need to show a home page at the first so i need to make an ednpoint for just / not register to show the things and not memebrs

    private final LoginProcessor lp;
    private final UserStore userStore;


    private final RedirectService redirectService;


    private final UserSession userSession;

    @Autowired
    public AuthController(LoginProcessor lp, UserStore userStore, RedirectService redirectService, UserSession userSession) {
        this.lp = lp;
        this.userStore = userStore;
        this.redirectService = redirectService;
        this.userSession = userSession; // Add this line
    }

    //--------------------------------------------------------------------------

// REGISTER
    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String RegisterPost(
            @ModelAttribute User user
    ){
        if (userStore.isEmpty()) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }

        userStore.save(user);

        return "redirect:/login";
    }

    //--------------------------------------------------------------------------

//LOGIN
    @GetMapping("/login")
    public String LoginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password
    ) {
        lp.setUsername(username);
        lp.setPassword(password);

        boolean isLoggedIn = lp.login();

        if (isLoggedIn) {
            // The controller just asks the service: "Where should I go now?"
            return redirectService.getRedirectUrlAfterLogin();
        } else {
            // The simple failure case remains.
            return "redirect:/login?error";
        }
    }

    //--------------------------------------------------------------------------

//Logout
    @PostMapping("/logout")
    public String processLogout() {

        userSession.setUsername(null);

        return "redirect:/login?logout";
    }
}
