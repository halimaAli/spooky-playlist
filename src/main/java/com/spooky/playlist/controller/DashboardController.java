package com.spooky.playlist.controller;


import com.spooky.playlist.model.User;
import com.spooky.playlist.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DashboardController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(final HttpSession session, final Model model) {
        User user = userService.getUser((String) session.getAttribute("accessToken"));
        session.setAttribute("userId", user.getId());
        model.addAttribute("username", user.getDisplay_name());
        return "main";
    }
}
