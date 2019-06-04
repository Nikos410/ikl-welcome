package de.nikos410.ikl.welcome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IklWelcomeController {

    @GetMapping("")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(Model model, @RequestParam("images") String images) {
        model.addAttribute("images", images.split(";"));
        return "welcome";
    }
}
