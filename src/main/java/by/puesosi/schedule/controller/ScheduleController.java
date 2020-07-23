package by.puesosi.schedule.controller;

import by.puesosi.schedule.entity.Institution;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

/**
 * The type Schedule controller.
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private Service service;
    private UserService userService;

    /**
     * Instantiates a new Schedule controller.
     */
    public ScheduleController() {
    }

    /**
     * Instantiates a new Schedule controller.
     *
     * @param service     the service
     * @param userService the user service
     */
    @Autowired
    public ScheduleController(Service service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * View access denied string.
     *
     * @return the string
     */
    @GetMapping("/error")
    public String viewAccessDenied(){
        return "error/access-denied";
    }

    /**
     * Choose schedule string.
     *
     * @param model the model
     * @return the string
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/")
    public String chooseSchedule(Model model) {
        List<Institution> institutions = (List<Institution>) model.asMap().get("institutions");
        Boolean findInstitutions = (Boolean) model.asMap().getOrDefault("findInstitutions", false);
        Boolean checkCity = (Boolean) model.asMap().get("checkCity");
        Boolean checkUniversitySchedule = (Boolean) model.asMap().get("checkUniversitySchedule");

        Boolean checkInstitution = (Boolean) model.asMap().get("checkInstitution");
        Boolean findGroups = (Boolean) model.asMap().getOrDefault("findGroups", false);

        String city = (String) model.asMap().get("city");
        String institution = (String) model.asMap().get("institution");

        model.addAttribute("cities", service.findCity());
        model.addAttribute("checkCity", checkCity);
        model.addAttribute("findInstitutions", findInstitutions);
        model.addAttribute("institutions", institutions);
        model.addAttribute("checkUniversitySchedule", checkUniversitySchedule);

        model.addAttribute("checkInstitution", checkInstitution);
        model.addAttribute("findGroups", findGroups);

        model.addAttribute("city", city);
        model.addAttribute("institution", institution);
        return "start";
    }

    /**
     * Gets institution.
     *
     * @param city               the city
     * @param redirectAttributes the redirect attributes
     * @return the institution
     */
    @PostMapping("/send-city")
    public String getInstitution(@RequestParam("city") String city, RedirectAttributes redirectAttributes) {
        boolean checkCity = service.checkCity(city);
        redirectAttributes.addFlashAttribute("checkCity", checkCity);
        if (!checkCity) {
            return "redirect:/schedule/";
        }
        List<Institution> institutions = service.findInstitutions(city);
        redirectAttributes.addFlashAttribute("institutions", institutions);
        redirectAttributes.addFlashAttribute("findInstitutions", true);
        if (!institutions.isEmpty()) {
            redirectAttributes.addFlashAttribute("checkUniversitySchedule", true);
        } else {
            redirectAttributes.addFlashAttribute("checkUniversitySchedule", false);
        }
        redirectAttributes.addFlashAttribute("city", city);
        return "redirect:/schedule/";
    }

    /**
     * Gets group.
     *
     * @param city               the city
     * @param institution        the institution
     * @param redirectAttributes the redirect attributes
     * @return the group
     */
    @PostMapping("/send-institution")
    public String getGroup(@RequestParam("city") String city, @RequestParam("institution") String institution,
                           RedirectAttributes redirectAttributes) {
        boolean checkInstitution = service.checkInstitution(city, institution);
        redirectAttributes.addFlashAttribute("checkInstitution", checkInstitution);
        redirectAttributes.addFlashAttribute("city", city);
        redirectAttributes.addFlashAttribute("institution", institution);
        if (!checkInstitution) {
            return "redirect:/schedule/";
        }
        redirectAttributes.addFlashAttribute("findGroups", true);
        return "redirect:/schedule/";
    }

    /**
     * Gets schedule.
     *
     * @param principal   the principal
     * @param model       the model
     * @param group       the group
     * @param city        the city
     * @param institution the institution
     * @return the schedule
     */
    @PostMapping("/get-group-schedule")
    public String getSchedule(Principal principal, Model model, @RequestParam("group") String group,
                              @RequestParam("city") String city,
                              @RequestParam("institution") String institution) {
        if (principal != null) {
            User user = userService.getCurrentUser(principal);
            model.addAttribute("haveGroup", userService.checkGroupAvailability(user, city, institution, group));
        }
        model.addAttribute("group", group);
        model.addAttribute("city", city);
        model.addAttribute("institution", institution);
        model.addAttribute("schedule", service.findGroupSchedule(group, city, institution));
        return "schedule-group";
    }


}
