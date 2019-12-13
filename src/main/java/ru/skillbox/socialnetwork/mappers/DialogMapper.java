package ru.skillbox.socialnetwork.mappers;

import ru.skillbox.socialnetwork.api.responses.BasicPerson;
import ru.skillbox.socialnetwork.api.responses.DialogMessage;
import ru.skillbox.socialnetwork.api.responses.DialogResponse;
import ru.skillbox.socialnetwork.api.responses.ReadStatus;
import ru.skillbox.socialnetwork.entities.Dialog;
import ru.skillbox.socialnetwork.entities.Message;
import ru.skillbox.socialnetwork.entities.Person;

import java.util.Date;

public class DialogMapper {
    public static DialogMessage getDialogMessage(Message message) {
        DialogMessage dialogMessage = new DialogMessage();
        dialogMessage.setId(message.getId());
        dialogMessage.setTime(message.getTime().getTime());
        BasicPerson authorResponse = new BasicPerson();
        Person author = message.getAuthor();
        authorResponse.setId(author.getId());
        authorResponse.setFirstName(author.getFirstName());
        authorResponse.setLastName(author.getLastName());
        authorResponse.setPhoto(author.getPhoto());
        Date lOnlinetime = author.getLastOnlineTime();
        if (lOnlinetime != null){
            authorResponse.setLastOnlineTime(author.getLastOnlineTime().getTime());
        }
        dialogMessage.setAuthor(authorResponse);
        dialogMessage.setRecipient(PersonToBasicPersonMapper.getBasicPerson(message.getRecipient()));
        dialogMessage.setText(message.getMessageText());
        dialogMessage.setReadStatus(ReadStatus.valueOf(message.getReadStatus()));
        return dialogMessage;
    }

    public static DialogResponse getDialogResponse(Dialog dialog, DialogMessage dialogMessage){
        DialogResponse dialogResponse = new DialogResponse();
        dialogResponse.setId(dialog.getId());
        dialogResponse.setUnreadCount(dialog.getUnreadCount());
        dialogResponse.setLastMessage(dialogMessage);
        return dialogResponse;
    }
}
