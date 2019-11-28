package ru.skillbox.socialnetwork.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.socialnetwork.services.PlatformService;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    /*@GetMapping("/languages")
    ResponseList<List<>>*/

}
