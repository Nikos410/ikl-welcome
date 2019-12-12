package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.model.Image;
import de.nikos410.ikl.welcome.model.NewsArticle;
import de.nikos410.ikl.welcome.service.ImageService;
import de.nikos410.ikl.welcome.service.NewsService;
import de.nikos410.ikl.welcome.service.SettingsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IklWelcomeController {

    private final ImageService imageService;
    private final NewsService newsService;
    private final SettingsService settingsService;

    public IklWelcomeController(ImageService imageService, NewsService newsService, SettingsService settingsService) {
        this.imageService = imageService;
        this.newsService = newsService;
        this.settingsService = settingsService;
    }

    @GetMapping("")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("settings", settingsService.getSettings());

        return "welcome";
    }

    @GetMapping("/nextimage/{previousId}")
    @ResponseBody
    public Image nextImage(@PathVariable Long previousId) {
        return imageService.getNextImage(previousId);
    }

    @GetMapping("/nextnews/{previousId}")
    @ResponseBody
    public NewsArticle nextNews(@PathVariable Long previousId) {
        return newsService.nextArticle(previousId);
    }
}
