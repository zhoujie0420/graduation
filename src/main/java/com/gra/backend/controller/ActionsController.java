package com.gra.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiezhou
 **/
@RestController
public class ActionsController {
    @GetMapping("/actions")
    public String actions() {
        return "GitHub Actions";
    }

}