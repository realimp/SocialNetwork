package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.Email;
import ru.skillbox.socialnetwork.api.requests.NotificationTypeRequest;
import ru.skillbox.socialnetwork.api.requests.Register;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.NotificationSettingsResponse;
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
  public Response<MessageResponse> register(@RequestBody Register register) {
    return accountService.register(register);
  }

  @PutMapping("/password/recovery")
  public Response<MessageResponse> passwordRecovery(@RequestBody Email email) throws UnsupportedEncodingException, MessagingException {
    return accountService.recovery(email);
  }

  @PutMapping("/password/set")
  public Response<MessageResponse> passwordSet(String token, String password) {
    return accountService.changePassword(token, password);
  }

  @PutMapping("/email")
  public Response<MessageResponse> email(String email) {
    return accountService.changeEmail(email);
  }

  @GetMapping("/notifications")
  public Response<NotificationSettingsResponse> notifications() {
    return accountService.getNotification();
  }

  @PutMapping("/notifications")
  public Response<MessageResponse> notifications(@RequestBody NotificationTypeRequest notificationTypeRequest) {
    return accountService.setNotification(notificationTypeRequest);
  }
}