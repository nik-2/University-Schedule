package by.puesosi.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * The type Login controller.
 */
@Controller
public class LoginController {

    /**
     * Gets login page.
     *
     * @param principal the principal
     * @return the login page
     */
    @GetMapping("/login")
    public String getLoginPage(Principal principal) {
        return principal == null ? "guest/login" : "redirect:/schedule/error";
    }
}
