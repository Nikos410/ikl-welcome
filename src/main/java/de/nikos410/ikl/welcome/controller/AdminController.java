package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.model.NewsArticle;
import de.nikos410.ikl.welcome.service.ImageService;
import de.nikos410.ikl.welcome.service.NewsService;
import de.nikos410.ikl.welcome.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final StorageService storageService;
    private final ImageService imageService;
    private final NewsService newsService;

    public AdminController(StorageService storageService, ImageService imageService, NewsService newsService) {
        this.storageService = storageService;
        this.imageService = imageService;
        this.newsService = newsService;
    }

    @GetMapping("")
    public String admin() {
        return "redirect:/admin/images";
    }

    @GetMapping("/images")
    public String adminImages(Model model) {
        model.addAttribute("allImages", imageService.findAll());
        return "adminImages";
    }

    @PostMapping("/images/upload")
    public ResponseEntity handleImageUpload(@RequestParam("file") MultipartFile uploadedImage) {
        storageService.store(uploadedImage);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/images/{id}/setinfo")
    public ResponseEntity setInfo(@PathVariable Long id, @RequestParam String info) {
        imageService.setInfoForImage(id, info);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/images/{id}/delete")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        storageService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/news")
    public String adminNews(Model model) {
        model.addAttribute("allArticles", newsService.getAll());
        return "adminNews";
    }

    @PostMapping("/admin/news/{id}/edit")
    public ResponseEntity editNews(@PathVariable Long id, NewsArticle editedArticle) {
        newsService.editArticle(id, editedArticle);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/news/{id}/delete")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        newsService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
