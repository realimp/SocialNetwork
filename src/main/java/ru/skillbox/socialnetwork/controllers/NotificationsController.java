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

    @GetMapping
    public ResponseList<List<NotificationBase>> notificationsForCurrentUser(Integer offset, Integer itemPerPage) {

        List<NotificationBase> notificationBaseList = notificationsService.notificationsForCurrentUser();

        MessageResponse message = new MessageResponse();
        message.setMessage("ok");

        ResponseList responseList = new ResponseList(message);
        long timestamp = new Date().getTime();
        responseList.setTimestamp(timestamp);
        responseList.setData(notificationBaseList);
        if(offset != null) {
            responseList.setOffset(offset);
        }
        if(itemPerPage != null) {
            responseList.setPerPage(itemPerPage);
        }

        return responseList;
    }

    @PutMapping
    public ResponseList<List<NotificationBase>> notificationsMarkAsRead(@RequestParam(value = "id", required = false) Integer id,
                                                                        @RequestParam(value = "all", required = false) Boolean all) {

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
