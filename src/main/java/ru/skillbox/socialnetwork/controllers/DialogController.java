package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.api.responses.Error;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.DialogRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;

import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/dialogs")
public class DialogController {
    //TODO: check method names after Dialog entity and DialogRepository are implemented

    @Autowired
    AccountService accountService;

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    PersonRepository personRepository;

    @GetMapping("/")
    public Response getDialogs(String query, int offset, int itemsPerPage) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PostMapping("/")
    public Response<DialogResponse> startDialog(int[] userIds) {
        Dialog dialog = new Dialog(accountService.getCurrentUser().getId());
        dialog.addUsers(userIds);
        Dialog savedDialog = (Dialog) dialogRepository.saveAndFlush(dialog);
        if (dialogRepository.findById(savedDialog.getId()) != null) {
            DialogResponse responseData = new DialogResponse();
            responseData.setId(savedDialog.getId());
            Response response = new Response(responseData);
            response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
            return response;
        }
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @GetMapping("/unreaded")
    public Response unreadCount() {
        int count = 0;
        for (Dialog dialog : dialogRepository.findByOwnerId(accountService.getCurrentUser().getId())) {
            count += dialog.getUnreadNumber();
        }
        DialogResponse responseData = new DialogResponse();
        responseData.setUnreadCount(count);
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @DeleteMapping("/{id}")
    public Response deleteDialog(@PathVariable int id) {
        dialogRepository.deleteById(id);
        if (dialogRepository.findById(id).isDeleted()) {
            DialogResponse responseData = new DialogResponse();
            responseData.setId(id);
            Response response = new Response(responseData);
            response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
            return response;
        }
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PutMapping("/{id}/users")
    public Response addUser(@PathVariable int id, int[] userIds) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @DeleteMapping("/{id}/users")
    public Response removeUsersFromDialog(@PathVariable int id, int[] userIds) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @GetMapping("/{id}/users/invite")
    public Response inviteUsers(@PathVariable int id, int[] userIds) {
        InviteLink responseData = new InviteLink();
        responseData.setLink(dialogRepository.findById(id).getInviteCode());
        if (responseData.getLink() == null) {
            Error error = new Error();
            error.setError(Error.ErrorType.INVALID_REQUEST);
            return new Response(error);
        }
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{id}/users/join")
    public Response joinDialog(@PathVariable int id, String invite) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @GetMapping("/{id}/messages")
    public Response findMessages(@PathVariable int id, String query, int offset, int itemsPerPage) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PostMapping("/{id}/messages")
    public Response sendMessage(@PathVariable int id, String text) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public Response deleteMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public Response editMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
    public Response recoverMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public Response markMessageRead(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        //TODO: implement method to mark message as read
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/activity/{user_id}")
    public Response getRecipientInfo(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        Person[] users = dialogRepository.findById(id).getUsers();
        for (Person user : users) {
            if (user.getId() == userId) {
                Optional<Person> person = personRepository.findById(userId);
                if (person.isPresent()) {
                    Activity responseData = new Activity();
                    responseData.setOnline(person.get().getOnline());
                    responseData.setLastActivity(person.get().getLastOnlineTime().getTime());
                    Response response = new Response(responseData);
                    response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
                    return response;
                }
            }
        }
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        error.setError_description(ErrorDescription.USER_NOT_FOUND);
        return new Response(error);
    }

    @PostMapping("/{id}/activity/{user_id}")
    public Response setActivityStatus(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        Person[] users = dialogRepository.findById(id).getUsers();
        for (Person user : users) {
            if (user.getId() == userId) {
                Optional<Person> person = personRepository.findById(userId);
                if (person.isPresent()) {
                    MessageResponse responseData = new MessageResponse();
                    responseData.setMessage("ok");
                    Response response = new Response(responseData);
                    response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
                    return response;
                }
            }
        }
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        error.setError_description(ErrorDescription.USER_NOT_FOUND);
        return new Response(error);
    }

    @GetMapping("/longpoll")
    public Response longPoll() {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }

    @PostMapping("/longpoll/history")
    public Response longPollHistory() {
        //TODO: implement method
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }
}
