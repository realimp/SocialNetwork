package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.Email;
import ru.skillbox.socialnetwork.api.requests.NotificationTypeRequest;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.NotificationParameter;
import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;
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

    return accountService.register(register);
  }

  @PutMapping("/password/recovery")
  public Response passwordRecovery(@RequestBody Email email) throws UnsupportedEncodingException, MessagingException {

    return accountService.recovery(email);
  }

  @PutMapping("/password/set")
  public Response passwordSet(String token, String password) {

    return accountService.changePassword(token, password);
  }

  @PutMapping("/email")
  public Response email(String email) {

    return accountService.changeEmail(email);
  }

  @GetMapping("/notifications")
  public Response notifications() {

    Response response = accountService.getNotification();
    response.setError("");
    return response;
  }

  @PutMapping("/notifications")
  public Response notifications(@RequestBody NotificationTypeRequest notificationTypeRequest) {

    return accountService.setNotification(notificationTypeRequest);
  }
}