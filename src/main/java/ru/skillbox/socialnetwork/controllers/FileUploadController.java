package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.api.responses.FileUploadResponse;
import ru.skillbox.socialnetwork.api.responses.Response;
import ru.skillbox.socialnetwork.entities.Person;
import ru.skillbox.socialnetwork.services.AccountService;
import ru.skillbox.socialnetwork.services.FileUploadService;

@RestController
@RequestMapping("/storage")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private AccountService accountService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<FileUploadResponse> fileUpload(@RequestParam("type") String type, @RequestBody MultipartFile file) {
        Person currentUser = accountService.getCurrentUser();
        if (currentUser == null) return new Response<>("Не удалось определить пользователя", null);
        return fileUploadService.fileUpload(file, currentUser.getId());
    }
}