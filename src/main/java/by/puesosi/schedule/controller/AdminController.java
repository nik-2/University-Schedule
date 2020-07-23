package by.puesosi.schedule.controller;

import by.puesosi.schedule.service.Service;
import by.puesosi.schedule.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

/**
 * The type Admin controller.
 */
@Controller
@RequestMapping("/schedule/admin")
public class AdminController {

    private Service service;
    private UserService userService;

    /**
     * Instantiates a new Admin controller.
     *
     * @param service     the service
     * @param userService the user service
     */
    @Autowired
    public AdminController(Service service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * Show profile string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/")
    public String showProfile(Model model) {
        model.addAttribute("groups", service.findGroups());
        Boolean error = (Boolean) model.asMap().getOrDefault("error", false);
        model.addAttribute("error", error);
        return "user/profile";
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
     * Delete group schedule string.
     *
     * @param redirectAttributes the redirect attributes
     * @param group              the group
     * @return the string
     */
    @PostMapping("/delete-group-schedule")
    public String deleteGroupSchedule(RedirectAttributes redirectAttributes,  @RequestParam String group) {
        List<String> params = Arrays.asList(group.split(" "));
        if(params.size() != 3){
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/schedule/admin/";
        }
        String cityName = params.get(0);
        String institutionName = params.get(1);
        String groupName = params.get(2);
        boolean error = userService.deleteGroupFromDB(cityName, institutionName, groupName);
        if(error){
            redirectAttributes.addFlashAttribute("error", true);
        }
        return "redirect:/schedule/admin/";
    }

    /**
     * Show group schedule string.
     *
     * @param redirectAttributes the redirect attributes
     * @param model              the model
     * @param group              the group
     * @return the string
     */
    @GetMapping("/show-group-schedule")
    public String showGroupSchedule(RedirectAttributes redirectAttributes, Model model, @RequestParam("group") String group){
        List<String> params = Arrays.asList(group.split(" "));
        if(params.size() != 3){
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/schedule/admin/";
        }
        String cityName = params.get(0);
        String institutionName = params.get(1);
        String groupName = params.get(2);
        model.addAttribute("group", groupName);
        model.addAttribute("city", cityName);
        model.addAttribute("institution", institutionName);
        if(service.findGroup(groupName, cityName, institutionName)){
            model.addAttribute("schedule", service.findGroupSchedule(groupName, cityName, institutionName));
        }
        else {
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/schedule/admin/";
        }
        return "schedule-group";
    }
}
