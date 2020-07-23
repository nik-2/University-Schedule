package by.puesosi.schedule.controller;

import by.puesosi.schedule.entity.User;
import by.puesosi.schedule.service.Service;
import by.puesosi.schedule.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 * The type User controller.
 */
@Controller
@RequestMapping("/schedule/user")
public class UserController {

    private UserService userService;
    private Service service;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     * @param service     the service
     */
    @Autowired
    public UserController(UserService userService, Service service) {
        this.userService = userService;
        this.service = service;
    }

    /**
     * Show profile string.
     *
     * @param principal the principal
     * @param model     the model
     * @return the string
     */
    @GetMapping("/")
    public String showProfile(Principal principal, Model model){
        User user = userService.getCurrentUser(principal);
        model.addAttribute("groups", user.getGroups());
        return "user/profile";
    }

    /**
     * Add group schedule string.
     *
     * @param principal   the principal
     * @param group       the group
     * @param city        the city
     * @param institution the institution
     * @return the string
     */
    @PostMapping("/add-group-schedule")
    public String addGroupSchedule(Principal principal,
                                   @RequestParam("group") String group,
                                   @RequestParam("city") String city,
                                   @RequestParam("institution") String institution) {
        User user = userService.getCurrentUser(principal);
        userService.addGroupSchedule(user, city, institution, group);
        return "redirect:/schedule/user/";
    }

    /**
     * Find group schedule string.
     *
     * @return the string
     */
    @GetMapping("/find-group-schedule")
    public String findGroupSchedule() {
        return "redirect:/schedule/";
    }

    /**
     * Show group schedule string.
     *
     * @param model the model
     * @param group the group
     * @return the string
     */
    @GetMapping("/show-group-schedule")
    public String showGroupSchedule(Model model, @RequestParam("group") String group) {
        List<String> params = Arrays.asList(group.split(", "));
        String cityName = params.get(0);
        String institutionName = params.get(1);
        String groupName = params.get(2);
        model.addAttribute("haveGroup", true);
        model.addAttribute("group", groupName);
        model.addAttribute("city", cityName);
        model.addAttribute("institution", institutionName);
        model.addAttribute("schedule", service.findGroupSchedule(groupName, cityName, institutionName));
        return "schedule-group";
    }

    /**
     * Delete schedule string.
     *
     * @param principal the principal
     * @param group     the group
     * @return the string
     */
    @PostMapping("/delete-group-schedule")
    public String deleteSchedule(Principal principal, @RequestParam("group") String group) {
        User user = userService.getCurrentUser(principal);
        List<String> params = Arrays.asList(group.split(", "));
        String cityName = params.get(0);
        String institutionName = params.get(1);
        String groupName = params.get(2);
        userService.deleteGroup(user, cityName, institutionName, groupName);
        return "redirect:/schedule/user/";
    }
}
