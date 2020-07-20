package by.puesosi.schedule.controller;

import by.puesosi.schedule.entity.Institution;
import by.puesosi.schedule.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private Service service;

    public ScheduleController() {
    }

    @Autowired
    public ScheduleController(Service service) {
        this.service = service;
    }

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
        if(!institutions.isEmpty()){
            redirectAttributes.addFlashAttribute("checkUniversitySchedule", true);
        }else {
            redirectAttributes.addFlashAttribute("checkUniversitySchedule", false);
        }
        redirectAttributes.addFlashAttribute("city", city);
        return "redirect:/schedule/";
    }

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

    @PostMapping("/get-group-schedule")
    public String getSchedule(Model model, @RequestParam("group") String group,
                              @RequestParam("city") String city,
                              @RequestParam("institution") String institution) {
        model.addAttribute("group", group);
        model.addAttribute("schedule", service.findGroupSchedule(group, city, institution));
        return "schedule-group";
    }


}
