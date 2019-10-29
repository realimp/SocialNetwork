package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.services.AccountService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @PostMapping("/register")
  public Response register(@RequestBody Register register) {

    return new Response(accountService.register(register));
  }

  @PutMapping("/password/recovery")
  public Response passwordRecovery(String email) throws UnsupportedEncodingException, MessagingException {

    return new Response(accountService.recovery(email));
  }

  @PutMapping("/password/set")
  public Response passwordSet(String token, String password) {

    return new Response(accountService.changePassword(token, password));
  }

  @PutMapping("/email")
  public Response email(String email) {

    return new Response(accountService.changeEmail(email));
  }

  @GetMapping("/notifications")
  public String notifications() {
    return "notifications";
  }

  @PutMapping("/notifications")
  public String notifications(Model model) {
    return "putNotifications";
  }
}