package com.spooky.playlist.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
