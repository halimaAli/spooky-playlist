package com.spooky.playlist.controller;

import com.spooky.playlist.service.AccessTokenService;
import com.spooky.playlist.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class MainController {
    private final AuthService authService;
    private final AccessTokenService accessTokenService;

    @GetMapping("/")
    public String index(final Model model) {
        model.addAttribute("url", authService.getAuthURL());
        return "index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(value = "code", required = false) final String code,
                           @RequestParam(value = "error", required = false) final String error,
                           final Model model,
                           final HttpSession session) {


        if (error != null) {
            System.out.println(error);
            return "error";
        }

        session.setAttribute("code", code);
        String token = accessTokenService.getToken(code);

        session.setAttribute("accessToken", token);
        model.addAttribute("accessToken", token);
        return "main";
    }
}
