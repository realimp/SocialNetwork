package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.api.responses.Error;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.repositories.DialogRepository;
import ru.skillbox.socialnetwork.repositories.MessageRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/")
    public ResponseList getDialogs(String query, int offset, int itemsPerPage) {
        Pageable resultsPage = PageRequest.of(offset, itemsPerPage);
        Page<Dialog> results =  dialogRepository.findByOwnerId(accountService.getCurrentUser().getId(), resultsPage);
        //TODO: implement query search

        ArrayList<DialogResponse> dialogResponses = new ArrayList<>();

        for (Dialog result : results) {
            DialogResponse dialogResponse = new DialogResponse();
            dialogResponse.setId(result.getId());
            dialogResponse.setUnreadCount(result.getUnreadCount());
//TODO            dialogResponse.setLastMessage();
//TODO            dialogResponse.setRecipientId();
//TODO            dialogResponse.setMessageText();
//TODO            dialogResponse.setReadStatus();
            dialogResponses.add(dialogResponse);
        }

        ResponseList response = new ResponseList(dialogResponses);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setTotal(results.getTotalElements());
        response.setOffset(offset);
        response.setPerPage(itemsPerPage);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        return response;
    }

    @PostMapping("/")
    public Response<DialogResponse> startDialog(int[] userIds) {
        Dialog dialog = new Dialog();
        dialog.setOwner(accountService.getCurrentUser());
        //TODO: add recipient(s)
        Dialog savedDialog = (Dialog) dialogRepository.saveAndFlush(dialog);

        DialogResponse responseData = new DialogResponse();
        responseData.setId(savedDialog.getId());
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setError("");
        return response;
    }

    @GetMapping("/unreaded")
    public Response unreadCount() {
        int count = dialogRepository.findByOwnerIdAndUnreadCount(accountService.getCurrentUser().getId()).size();
        DialogResponse responseData = new DialogResponse();
        responseData.setUnreadCount(count);
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @DeleteMapping("/{id}")
    public Response deleteDialog(@PathVariable int id) {
        dialogRepository.deleteById(id);
        DialogResponse responseData = new DialogResponse();
        responseData.setId(id);
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{id}/users")
    public Response addUser(@PathVariable int id, int[] userIds) {
        //TODO: implement method
        Error error = new Error();

        return new Response(error);
    }

    @DeleteMapping("/{id}/users")
    public Response removeUsersFromDialog(@PathVariable int id, int[] userIds) {
        //TODO: implement method
        Error error = new Error();

        return new Response(error);
    }

    @GetMapping("/{id}/users/invite")
    public Response inviteUsers(@PathVariable int id, int[] userIds) {
        InviteLink responseData = new InviteLink();
        responseData.setLink(dialogRepository.findById(id).get().getInviteCode());
        if (responseData.getLink() == null) {
            Error error = new Error();

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

        return new Response(error);
    }

    @GetMapping("/{id}/messages")
    public Response findMessages(@PathVariable int id, String query, int offset, int itemsPerPage) {
        //TODO: implement method
        Error error = new Error();

        return new Response(error);
    }

    @PostMapping("/{id}/messages")
    public Response sendMessage(@PathVariable int id, String text) {
        //TODO: implement method
        Error error = new Error();

        return new Response(error);
    }

    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public Response deleteMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        if (dialog.isPresent()) {
            dialog.get().getMessages().remove(messageRepository.findById(messageId));
        }
        DialogMessage responseData = new DialogMessage();
        responseData.setId(messageId);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public Response editMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        //TODO: implement method
        Error error = new Error();

        return new Response(error);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
    public Response recoverMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        DialogMessage responseData = new DialogMessage();
        if (dialog.isPresent()) {
            List<Message> messages = dialog.get().getMessages();
            messages.add(messageRepository.findById(messageId).get());
            dialog.get().setMessages(messages);

            //TODO add response
        }
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
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
        //TODO: implement method
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PostMapping("/{id}/activity/{user_id}")
    public Response setActivityStatus(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        //TODO: implement method
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }
}
