package com.example.ProjectClub.controllers;

import com.example.ProjectClub.store.MembershipStore;
import com.example.ProjectClub.store.UserStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private MembershipStore membershipStore;

    public HomeController(MembershipStore membershipStore) {
        this.membershipStore = membershipStore;
    }


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("memberships", membershipStore.findAll());
        return "home";
    }


}
