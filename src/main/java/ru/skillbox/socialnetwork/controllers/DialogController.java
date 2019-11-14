package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.DialogInvite;
import ru.skillbox.socialnetwork.api.requests.MessageText;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.repositories.DialogRepository;
import ru.skillbox.socialnetwork.repositories.MessageRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping("")
    public ResponseList getDialogs(@RequestParam(required = false) String query, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemsPerPage) {
        Pageable resultsPage;
        if (offset != null && itemsPerPage != null) {
            resultsPage = PageRequest.of(offset, itemsPerPage);
        } else {
            resultsPage = PageRequest.of(0, 20);
        }
        Page<Dialog> results =  dialogRepository.findByOwnerId(accountService.getCurrentUser().getId(), resultsPage);

        ArrayList<DialogResponse> dialogResponses = new ArrayList<>();

        if (results.getTotalElements() > 0) {

            //TODO: implement query search

            for (Dialog result : results) {
                Pageable sortByDate = PageRequest.of(0, 1, Sort.by("time"));
                Page<Message> lastMessagePage = messageRepository.findByDialogId(result.getId(), sortByDate);
                if (lastMessagePage.getTotalElements() > 0) {
                    Message lastMessage = lastMessagePage.getContent().get(0);
                    DialogResponse dialogResponse = new DialogResponse();
                    dialogResponse.setId(result.getId());
                    dialogResponse.setUnreadCount(result.getUnreadCount());

                    DialogMessage message = new DialogMessage();
                    message.setId(lastMessage.getId());
                    message.setTime(lastMessage.getTime().getTime());

                    BasicPerson authorResponse = new BasicPerson();
                    Person author = lastMessage.getAuthor();
                    authorResponse.setId(author.getId());
                    authorResponse.setFirstName(author.getFirstName());
                    authorResponse.setLastName(author.getLastName());
                    authorResponse.setPhoto(author.getPhoto());
                    authorResponse.setLastOnlineTime(author.getLastOnlineTime().getTime());

                    message.setAuthor(authorResponse);
                    message.setRecipientId(lastMessage.getRecipient().getId());
                    message.setText(lastMessage.getMessageText());
                    message.setReadStatus(ReadStatus.valueOf(lastMessage.getReadStatus()));

                    dialogResponse.setLastMessage(message);
                    dialogResponses.add(dialogResponse);
                }
            }
        }

        ResponseList response = new ResponseList(dialogResponses);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setTotal(results.getTotalElements());
        response.setOffset(resultsPage.getOffset());
        response.setPerPage(resultsPage.getPageSize());
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());

        return response;
    }

    @PostMapping("")
    public Response startDialog(@RequestBody UserIds userIds) {
        Dialog dialog = new Dialog();
        dialog.setOwner(accountService.getCurrentUser());
        List<Person> recipients = new ArrayList<>();
        for (int id : userIds.getIds()) {
            recipients.add(personRepository.findById(id).get());
        }
        dialog.setRecipients(recipients);
        Dialog savedDialog = dialogRepository.saveAndFlush(dialog);

        DialogResponse responseData = new DialogResponse();
        responseData.setId(savedDialog.getId());
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setError("");
        return response;
    }

    @GetMapping("/unreaded")
    public Response unreadCount() {
        int count = 0;
        List<Dialog> dialogs = dialogRepository.findByOwnerId(accountService.getCurrentUser().getId());
        for (Dialog dialog : dialogs) {
            count += dialog.getUnreadCount();
        }
        DialogResponse responseData = new DialogResponse();
        responseData.setUnreadCount(count);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }


    @DeleteMapping("/{id}")
    public Response deleteDialog(@PathVariable int id) {
        Dialog dialog = dialogRepository.findById(id).get();
        dialog.setDeleted(true);
        dialogRepository.saveAndFlush(dialog);
        DialogResponse responseData = new DialogResponse();
        responseData.setId(id);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{id}/users")
    public Response addUser(@PathVariable int id, @RequestBody UserIds userIds) {
        Dialog dialog = dialogRepository.findById(id).get();
        List<Person> recipients = dialog.getRecipients();
        for (int userId : userIds.getIds()) {
            recipients.add(personRepository.findById(userId).get());
        }
        dialog.setRecipients(recipients);
        dialogRepository.saveAndFlush(dialog);

        UserIds responseData = new UserIds();
        responseData.setIds(userIds.getIds());
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @DeleteMapping("/{id}/users/{user_ids}")
    public Response removeUsersFromDialog(@PathVariable("id") int id, @PathVariable("user_ids") String[] userIds) {
        Dialog dialog = dialogRepository.findById(id).get();
        List<Person> recipients = dialog.getRecipients();
        for (String userId : userIds) {
            recipients.remove(personRepository.findById(Integer.parseInt(userId)));
        }
        dialog.setRecipients(recipients);
        dialogRepository.saveAndFlush(dialog);

        int[] ids = new int[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            ids[i] = Integer.parseInt(userIds[i]);
        }

        UserIds responseData = new UserIds();
        responseData.setIds(ids);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/users/invite")
    public Response inviteUsers(@PathVariable int id) {
        String inviteLink = contextPath + "dialogs/" + id + "/users/join?invite=" + dialogRepository.findById(id).get().getInviteCode();
        InviteLink responseData = new InviteLink();
        responseData.setLink(inviteLink);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{id}/users/join")
    public Response joinDialog(@PathVariable int id, @RequestBody DialogInvite link) {
        Dialog dialog = dialogRepository.findById(id).get();
        List<Person> recipients = dialog.getRecipients();
        UserIds responseData = new UserIds();
        if (dialog.getInviteCode().equalsIgnoreCase(link.getLink())) {
            recipients.add(accountService.getCurrentUser());
        }
        dialog.setRecipients(recipients);
        Dialog updatedDialog = dialogRepository.saveAndFlush(dialog);

        List<Person> updatedRecipients = updatedDialog.getRecipients();
        int[] recipientIds = new int[updatedRecipients.size()];
        for (int i = 0; i < updatedRecipients.size(); i++) {
            recipientIds[i] = updatedRecipients.get(i).getId();
        }
        responseData.setIds(recipientIds);
        Response response = new Response(recipientIds);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/messages")
    public ResponseList findMessages(@PathVariable int id, @RequestParam(required = false) String query, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemsPerPage) {
        Pageable pageable;
        if (offset != null && itemsPerPage != null) {
            pageable = PageRequest.of(offset, itemsPerPage);
        } else {
            pageable = PageRequest.of(0, 20);
        }
        Page<Message> results = messageRepository.findByDialogIdAndMessageText(id, query, pageable);

        DialogMessageList responseData = new DialogMessageList();

        for (Message message : results) {
            DialogMessage messageResponse = new DialogMessage();
            messageResponse.setId(message.getId());
            messageResponse.setTime(message.getTime().getTime());

            BasicPerson author = new BasicPerson();
            Person messageAuthor = message.getAuthor();
            author.setId(messageAuthor.getId());
            author.setFirstName(messageAuthor.getFirstName());
            author.setLastName(messageAuthor.getLastName());
            author.setPhoto(messageAuthor.getPhoto());
            author.setLastOnlineTime(messageAuthor.getLastOnlineTime().getTime());

            messageResponse.setAuthor(author);
            messageResponse.setRecipientId(message.getRecipient().getId());
            messageResponse.setText(message.getMessageText());
            messageResponse.setReadStatus(ReadStatus.valueOf(message.getReadStatus()));
            responseData.addMessage(messageResponse);
        }

        ResponseList response = new ResponseList(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setTotal(messageRepository.findByDialogIdAndMessageText(id, query).size());
        response.setOffset(pageable.getOffset());
        response.setPerPage(pageable.getPageSize());
        return response;
    }

    @PostMapping("/{id}/messages")
    public Response sendMessage(@PathVariable int id, @RequestBody MessageText messageText) {
        Optional<Dialog> dialog = dialogRepository.findById(id);
        DialogMessage responseData = new DialogMessage();
        if (dialog.isPresent()) {
            Person owner = accountService.getCurrentUser();
            Message message = new Message();
            message.setDialog(dialog.get());
            message.setAuthor(owner);
            message.setMessageText(messageText.getText());
            message.setReadStatus(ReadStatus.SENT.toString());
            message.setTime(new Date());
            message.setRecipient(dialog.get().getRecipients().get(0));
            message.setDeleted(false);
            Message savedMessage = messageRepository.saveAndFlush(message);

            responseData.setId(savedMessage.getId());
            responseData.setTime(savedMessage.getTime().getTime());

            BasicPerson author = new BasicPerson();
            author.setId(owner.getId());
            author.setFirstName(owner.getFirstName());
            author.setLastName(owner.getLastName());
            author.setPhoto(owner.getPhoto());
            author.setLastOnlineTime(owner.getLastOnlineTime().getTime());

            responseData.setAuthor(author);
            responseData.setRecipientId(savedMessage.getRecipient().getId());
            responseData.setText(messageText.getText());
            responseData.setReadStatus(ReadStatus.valueOf(savedMessage.getReadStatus()));
        }
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public Response deleteMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        if (dialog.isPresent()) {
            messageRepository.findById(messageId).get().setDeleted(true);
        }
        DialogMessage responseData = new DialogMessage();
        responseData.setId(messageId);
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public Response editMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId, @RequestBody MessageText messageText) {
        Message message = messageRepository.findById(messageId).get();
        message.setMessageText(messageText.getText());
        Message editedMessage = messageRepository.saveAndFlush(message);

        DialogMessage responseData = new DialogMessage();
        responseData.setId(editedMessage.getId());
        responseData.setTime(editedMessage.getTime().getTime());

        Person messageAuthor = editedMessage.getAuthor();
        BasicPerson author = new BasicPerson();
        author.setId(messageAuthor.getId());
        author.setFirstName(messageAuthor.getFirstName());
        author.setLastName(messageAuthor.getLastName());
        author.setPhoto(messageAuthor.getPhoto());
        author.setLastOnlineTime(messageAuthor.getLastOnlineTime().getTime());

        responseData.setAuthor(author);
        responseData.setRecipientId(editedMessage.getRecipient().getId());
        responseData.setReadStatus(ReadStatus.valueOf(editedMessage.getReadStatus()));

        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
    public Response recoverMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        DialogMessage responseData = new DialogMessage();
        if (dialog.isPresent()) {
            Message message = messageRepository.findById(messageId).get();
            message.setDeleted(false);
            Message recoveredMessage = messageRepository.saveAndFlush(message);

            Person sender = recoveredMessage.getAuthor();
            BasicPerson author = new BasicPerson();
            author.setId(sender.getId());
            author.setFirstName(sender.getFirstName());
            author.setLastName(sender.getLastName());
            author.setPhoto(sender.getPhoto());
            author.setLastOnlineTime(sender.getLastOnlineTime().getTime());

            responseData.setId(recoveredMessage.getId());
            responseData.setTime(recoveredMessage.getTime().getTime());
            responseData.setAuthor(author);
            responseData.setRecipientId(recoveredMessage.getRecipient().getId());
            responseData.setText(recoveredMessage.getMessageText());
            responseData.setReadStatus(ReadStatus.valueOf(recoveredMessage.getReadStatus()));
        }
        Response response = new Response(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public Response markMessageRead(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        messageRepository.findById(messageId).get().setReadStatus(ReadStatus.READ.toString());
        Dialog dialog = dialogRepository.findById(dialogId).get();
        dialog.setUnreadCount(dialog.getUnreadCount() - 1);
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/activity/{user_id}")
    public Response getRecipientInfo(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        Person user = personRepository.findById(userId).get();
        Activity responseData = new Activity();
        responseData.setOnline(user.getOnline());
        responseData.setLastActivity(user.getLastOnlineTime().getTime());
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
