package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.socialnetwork.api.responses.MessageResponse;
import ru.skillbox.socialnetwork.api.responses.NotificationBase;
import ru.skillbox.socialnetwork.api.responses.ResponseList;
import ru.skillbox.socialnetwork.services.NotificationsService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @GetMapping("/")
    public ResponseList<List<NotificationBase>> notificationsForCurrentUser(int offset, int itemPerPage) {

        List<NotificationBase> notificationBaseList = notificationsService.notificationsForCurrentUser();

        MessageResponse message = new MessageResponse();
        message.setMessage("ok");

        ResponseList responseList = new ResponseList(message);
        long timestamp = new Date().getTime();
        responseList.setTimestamp(timestamp);
        responseList.setData(notificationBaseList);
        responseList.setOffset(offset);
        responseList.setPerPage(itemPerPage);

        return responseList;
    }

    @PutMapping("/")
    public ResponseList<List<NotificationBase>> notificationsMarkAsRead(@PathVariable Integer id, boolean all) {

        List<NotificationBase> notificationBaseList = notificationsService.notificationsMarkAsRead(id, all);

        MessageResponse message = new MessageResponse();
        message.setMessage("ok");

        ResponseList responseList = new ResponseList(message);
        long timestamp = new Date().getTime();
        responseList.setTimestamp(timestamp);
        responseList.setData(notificationBaseList);

        return responseList;
    }
}
