package by.puesosi.schedule.controller;

import by.puesosi.schedule.entity.User;
import by.puesosi.schedule.service.registration.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * The type Registration controller.
 */
@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    /**
     * Instantiates a new Registration controller.
     *
     * @param registrationService the registration service
     */
    public RegistrationController(RegistrationService registrationService){
        this.registrationService = registrationService;
    }

    /**
     * Get registration page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "guest/register";
    }

    /**
     * Check registration info string.
     *
     * @param user          the user
     * @param bindingResult the binding result
     * @param model         the model
     * @return the string
     */
    @PostMapping("/registration")
    public String checkRegistrationInfo(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "guest/register";
        }
        String resultOfCheck = registrationService.checkNewUserData(user);
        switch (resultOfCheck){
            case "usernameError":
                model.addAttribute("usernameError", "User with this username already exists");
                return "guest/register";
            case "emailError":
                model.addAttribute("emailError", "User with this email already exists");
                return "guest/register";
            case "usernameAndEmailError":
                model.addAttribute("emailAndUsernameError", "User with this username and email already exists");
                return "guest/register";
            default:
                return "redirect:/login";
        }
    }
}
