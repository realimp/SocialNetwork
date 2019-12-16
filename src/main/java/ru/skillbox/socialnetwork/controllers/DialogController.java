package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.requests.DialogInvite;
import ru.skillbox.socialnetwork.api.requests.MessageText;
import ru.skillbox.socialnetwork.api.responses.*;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.mappers.DialogMapper;
import ru.skillbox.socialnetwork.mappers.PersonToBasicPersonMapper;
import ru.skillbox.socialnetwork.repositories.DialogRepository;
import ru.skillbox.socialnetwork.repositories.MessageRepository;
import ru.skillbox.socialnetwork.repositories.PersonRepository;
import ru.skillbox.socialnetwork.services.AccountService;

import java.sql.Timestamp;
import java.util.*;

import static java.sql.Types.NULL;

@RestController
@RequestMapping("/dialogs")
public class DialogController {
    //TODO: check method names after Dialog entity and DialogRepository are implemented

    @Autowired
    private AccountService accountService;

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private HashSet<Integer> setRecipients = new HashSet<Integer>();
    private List<Dialog> allExistsDialog = new ArrayList<Dialog>();

    @GetMapping
    public ResponseList getDialogs(@RequestParam(required = false) String query, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemsPerPage) {
        Pageable resultsPage;
        if (offset != null && itemsPerPage != null) {
            resultsPage = PageRequest.of(offset, itemsPerPage);
        } else {
            resultsPage = PageRequest.of(0, 20);
        }
        ArrayList<DialogResponse> dialogResponses = new ArrayList<>();
        if (isExistDialogs()) {
            Page<Dialog> results = new PageImpl<>(allExistsDialog, resultsPage, allExistsDialog.size());
            for (Dialog result : results) {
                Pageable sortByDate = PageRequest.of(0, 1, Sort.by("time"));
                Page<Message> lastMessagePage = messageRepository.findByDialogId(result.getId(), sortByDate);
                DialogMessage message = new DialogMessage();
                if (lastMessagePage.stream().count() > 0) {
                    message = DialogMapper.getDialogMessage(lastMessagePage.getContent().get(0));
                }
                dialogResponses.add(DialogMapper.getDialogResponse(result, message));
            }
        }
        return new ResponseList<>(dialogResponses, dialogResponses.size());
    }

    @PostMapping
    public Response startDialog(@RequestBody UserIds users_ids) {
        DialogResponse responseData = new DialogResponse();
        List<Person> recipients = newRecipients(users_ids);
        if (!recipients.isEmpty()) {
            Dialog savedDialog = makeDialog(recipients, accountService.getCurrentUser());
            responseData.setId(savedDialog.getId());
        } else {
            responseData.setId(users_ids.getIds()[0]);
        }
        Response response = new Response<>(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        response.setError("");
        return response;
    }

    private boolean isExistDialogs() {
        List<Dialog> allUserDialogs = dialogRepository.findByOwnerId(accountService.getCurrentUser().getId());
        if (!allUserDialogs.isEmpty()) {
            allExistsDialog = allUserDialogs;
            for (Dialog curDialog : allUserDialogs) {
                List<Person> recipientsDialog = new ArrayList<Person>();
                recipientsDialog = curDialog.getRecipients();
                for (Person curRecipient : recipientsDialog) {
                    setRecipients.add(curRecipient.getId());
                }
            }
        }
        return !allUserDialogs.isEmpty();
    }

    private List<Person> newRecipients(UserIds users_ids) {
        List<Person> listRecipients = new ArrayList<>();
        if (isExistDialogs()) {
            for (int id : users_ids.getIds()) {
                if (!setRecipients.contains(id)) {
                    listRecipients.add(personRepository.findById(id).get());
                }
            }
        } else if (users_ids.getIds().length > 0) {
            for (int id : users_ids.getIds()) {
                listRecipients.add(personRepository.findById(id).get());
            }
        }
        return listRecipients;
    }

    private Dialog makeDialog(List<Person> rEcipients, Person oWner) {
        Dialog dialog = new Dialog();
        dialog.setOwner(oWner);
        dialog.setRecipients(rEcipients);
        Person owner = accountService.getCurrentUser();
        MessageText mText = new MessageText("");
        Message savedMessage = makeMessage(dialog,owner,mText, ReadStatus.SENT);
        return dialogRepository.saveAndFlush(dialog);
    }

    private Message makeMessage(Dialog newdialog, Person dOwner, MessageText messagetext, ReadStatus readstatus){
        Message message = new Message();
        message.setDialog(newdialog);
        message.setAuthor(dOwner);
        if (messagetext.getText().length()>0) {
            message.setMessageText(messagetext.getText());
        }
        message.setReadStatus(readstatus.toString());
        message.setTime(new Date());
        message.setRecipient(newdialog.getRecipients().get(0));
        message.setDeleted(false);
        return messageRepository.saveAndFlush(message);
    }

    @GetMapping("/unreaded")
    public Response unreadCount() {
        int count = 0;
        List<Dialog> dialogs = dialogRepository.findByOwnerId(accountService.getCurrentUser().getId());
        if (dialogs.size() > 0) {
            for (Dialog dialog : dialogs) {
                if (dialog.getUnreadCount() == null) count += 0;
                else count += dialog.getUnreadCount();
            }
        }
        UnreadCount responseData = new UnreadCount(count);
        Response response = new Response<>(responseData);
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
        Response response = new Response<>(responseData);
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
        Response response = new Response<>(responseData);
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
        Response response = new Response<>(responseData);
        response.setError("");
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/users/invite")
    public Response inviteUsers(@PathVariable int id) {
        String inviteLink = contextPath + "dialogs/" + id + "/users/join?invite=" + dialogRepository.findById(id).get().getInviteCode();
        InviteLink responseData = new InviteLink();
        responseData.setLink(inviteLink);
        Response response = new Response<>(responseData);
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
        return new Response<>(recipientIds);
    }

    @GetMapping("/{id}/messages")
    public ResponseList findMessages(@PathVariable int id, @RequestParam(required = false) String query, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer itemsPerPage) {
        Pageable pageable;
        if (offset != null && itemsPerPage != null) {
            pageable = PageRequest.of(offset, itemsPerPage);
        } else {
            pageable = PageRequest.of(0, 20);
        }
        Page<Message> results;
        if (query != null && !"".equals(query))
            results = messageRepository.findByDialogIdAndMessageText(id, query, pageable);
        else
            results = messageRepository.findByDialogId(id, pageable);
        List<DialogMessage> responseData = new ArrayList<>();

        for (Message message : results) {
            responseData.add(DialogMapper.getDialogMessage(message));
        }

        return new ResponseList<>(responseData, messageRepository.findByDialogId(id).size());
    }

    @PostMapping("/{id}/messages")
    public Response sendMessage(@PathVariable int id, @RequestBody MessageText messageText) {
        Optional<Dialog> dialog = dialogRepository.findById(id);
        Person owner = accountService.getCurrentUser();
        List<Person> recipients = dialog.get().getRecipients();
        List<Dialog> specularDialogs = new ArrayList<Dialog>();
        List<Person> owners = new ArrayList<>();
        owners.add(owner);
        for (Person recipient: recipients) {
            List<Dialog> sDialogs = dialogRepository.findByOwnerAndRecipients(recipient, owners);
            if (!specularDialogs.contains(sDialogs)){
                specularDialogs.addAll(sDialogs);
            }
        }
        DialogMessage responseData = new DialogMessage();
        if (dialog.isPresent()) {

            Message savedMessage = makeMessage(dialog.get(), owner, messageText, ReadStatus.SENT);
            Message specularMessage = makeMessage(specularDialogs.get(0),owner,messageText, ReadStatus.READ);

            responseData.setId(savedMessage.getId());
            responseData.setTime(savedMessage.getTime().getTime());

            BasicPerson author = new BasicPerson();
            author.setId(owner.getId());
            author.setFirstName(owner.getFirstName());
            author.setLastName(owner.getLastName());
            author.setPhoto(owner.getPhoto());
            author.setLastOnlineTime(owner.getLastOnlineTime().getTime());

            responseData.setAuthor(author);
            responseData.setRecipient(PersonToBasicPersonMapper.getBasicPerson(savedMessage.getRecipient()));
            responseData.setText(messageText.getText());
            responseData.setReadStatus(ReadStatus.valueOf(savedMessage.getReadStatus()));
        }
        return new Response<>(responseData);
    }



    @DeleteMapping("/{dialog_id}/messages/{message_id}")
    public Response deleteMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        if (dialog.isPresent()) {
            messageRepository.findById(messageId).get().setDeleted(true);
        }
        DialogMessage responseData = new DialogMessage();
        responseData.setId(messageId);
        return new Response<>(responseData);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}")
    public Response editMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId, @RequestBody MessageText messageText) {
        Message message = messageRepository.findById(messageId).get();
        message.setMessageText(messageText.getText());
        Message editedMessage = messageRepository.saveAndFlush(message);

        DialogMessage responseData = DialogMapper.getDialogMessage(editedMessage);
        return new Response<>(responseData);
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/recover")
    public Response recoverMessage(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        Optional<Dialog> dialog = dialogRepository.findById(dialogId);
        if (!dialog.isPresent()) return new Response<>("Не найден диалог " + dialogId, null);
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (!messageOptional.isPresent()) return new Response<>("Не найдено сообщение " + messageId, null);
        Message message = messageOptional.get();
        message.setDeleted(false);
        Message recoveredMessage = messageRepository.saveAndFlush(message);
        return new Response<>(DialogMapper.getDialogMessage(recoveredMessage));
    }

    @PutMapping("/{dialog_id}/messages/{message_id}/read")
    public Response markMessageRead(@PathVariable("dialog_id") int dialogId, @PathVariable("message_id") int messageId) {
        messageRepository.findById(messageId).get().setReadStatus(ReadStatus.READ.toString());
        Dialog dialog = dialogRepository.findById(dialogId).get();
        dialog.setUnreadCount(dialog.getUnreadCount() - 1);
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response<>(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @GetMapping("/{id}/activity/{user_id}")
    public Response getRecipientInfo(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        Person user = personRepository.findById(userId).get();
        Activity responseData = new Activity();
        responseData.setOnline(user.getOnline());
        responseData.setLastActivity(user.getLastOnlineTime().getTime());
        Response response = new Response<>(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }

    @PostMapping("/{id}/activity/{user_id}")
    public Response setActivityStatus(@PathVariable("id") int id, @PathVariable("user_id") int userId) {
        //TODO: implement method
        MessageResponse responseData = new MessageResponse();
        responseData.setMessage("ok");
        Response response = new Response<>(responseData);
        response.setTimestamp(new Timestamp(System.currentTimeMillis()).getTime());
        return response;
    }
}
