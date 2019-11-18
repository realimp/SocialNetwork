package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skillbox.socialnetwork.api.responses.Error;
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
    public Response fileUpload(@RequestParam("type") String type, @RequestBody MultipartFile file) {
        if (file != null && !file.isEmpty() && file.getContentType().toUpperCase().contains(type.toUpperCase())) {
            return fileUploadService.fileUpload(file, accountService.getCurrentUser());
        }
        Error error = new Error();
        error.setError(Error.ErrorType.INVALID_REQUEST);
        return new Response(error);
    }
}
