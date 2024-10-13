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
public class AuthController {

    private final AuthService authService;
    private final AccessTokenService accessTokenService;

    @GetMapping("/login")
    public String loginWithSpotify() {
        return "redirect:" + authService.getAuthURL(); // directed to the spotify auth page
    }

    @GetMapping("/callback")
    public String handleSpotifyCallback(@RequestParam(value = "code", required = false) final String code,
                                        @RequestParam(value = "error", required = false) final String error,
                                        final Model model,
                                        final HttpSession session) {
        if (error != null) {
            System.out.println("ERROR");
            return "error";
        }

        session.setAttribute("code", code);
        String token = accessTokenService.getToken(code);

        session.setAttribute("accessToken", token);
        model.addAttribute("accessToken", token);
        return "redirect:/dashboard";
    }
}
