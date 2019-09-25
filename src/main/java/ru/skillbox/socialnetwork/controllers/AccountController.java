package ru.skillbox.socialnetwork.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {
    @PostMapping("/register")
    public String register(){
        return "register";
    }

    @PutMapping("/password/recovery")
    public String passwordRecovery(){
        return "passwordRecovery";
    }

    @PutMapping("/password/set")
    public String passwordSet(){
        return "passwordSet";
    }
    @PutMapping("/email")
    public String email(){
        return "email";
    }
    @GetMapping("/notifications")
    public String notifications(){
        return "notifications";
    }
    @PutMapping("/notifications")
    public String notifications(Model model){
        return "notifications";
    }

}
