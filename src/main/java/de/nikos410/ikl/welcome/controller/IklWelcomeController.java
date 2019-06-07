package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.model.Image;
import de.nikos410.ikl.welcome.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;

@Controller
public class IklWelcomeController {

    private final ImageService imageService;

    public IklWelcomeController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/nextimage")
    @ResponseBody
    public Image nextImage() {
        return imageService.getNextImage();
    }

    @GetMapping("/images/{fileName}/getinfo")
    @ResponseBody
    public String getInfoForImage(@PathVariable String fileName) {
        return imageService.getInfoForImage(fileName);
    }
}
