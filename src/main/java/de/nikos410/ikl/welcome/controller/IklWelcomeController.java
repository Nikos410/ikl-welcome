package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.model.Image;
import de.nikos410.ikl.welcome.model.NewsArticle;
import de.nikos410.ikl.welcome.service.ImageService;
import de.nikos410.ikl.welcome.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IklWelcomeController {

    private final ImageService imageService;
    private final NewsService newsService;

    public IklWelcomeController(ImageService imageService, NewsService newsService) {
        this.imageService = imageService;
        this.newsService = newsService;
    }

    @GetMapping("")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/nextimage/{lastId}")
    @ResponseBody
    public Image nextImage(@PathVariable Long lastId) {
        return imageService.getNextImage(lastId);
    }

    @GetMapping("/nextnews/{lastId}")
    @ResponseBody
    public NewsArticle nextNews(@PathVariable Long lastId) {
        return newsService.nextArticle(lastId);
    }
}
