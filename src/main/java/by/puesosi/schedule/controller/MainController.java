package by.puesosi.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Main controller.
 */
@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Index string.
     *
     * @return the string
     */
    @GetMapping
    public String index(){
        return "redirect:/schedule/";
    }
}
