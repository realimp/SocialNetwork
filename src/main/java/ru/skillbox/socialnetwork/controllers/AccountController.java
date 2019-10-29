package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.services.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @PostMapping("/register")
  public MessageResponse register(Register register) {

    return accountService.register(register);
  }

  @PutMapping("/password/recovery")
  public MessageResponse passwordRecovery(String email) {

    return accountService.recovery(email);
  }

  @PutMapping("/password/set")
  public MessageResponse passwordSet(String token, String password) {

    return accountService.changePassword(token, password);
  }

  @PutMapping("/email")
  public MessageResponse email(String email) {

    return accountService.changeEmail(email);
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
