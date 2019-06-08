package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.exception.StorageException;
import de.nikos410.ikl.welcome.model.NewsArticle;
import de.nikos410.ikl.welcome.service.ImageService;
import de.nikos410.ikl.welcome.service.NewsService;
import de.nikos410.ikl.welcome.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final StorageService storageService;
    private final ImageService imageService;
    private final NewsService newsService;

    public AdminController(StorageService storageService, ImageService imageService, NewsService newsService) {
        this.storageService = storageService;
        this.imageService = imageService;
        this.newsService = newsService;
    }

    @GetMapping("/admin")
    public String admin() {
        return "redirect:/admin/images";
    }

    @GetMapping("/admin/images")
    public String adminImages(Model model) {
        model.addAttribute("allImages", imageService.findAll());
        return "adminImages";
    }

    @PostMapping("/admin/images/upload")
    public String handleImageUpload(@RequestParam("file") MultipartFile uploadedImage, RedirectAttributes redirectAttributes) {
        try {
            storageService.store(uploadedImage);
            redirectAttributes.addFlashAttribute("uploadSuccess", true);
        } catch (StorageException e) {
            LOG.error("Could not store uploaded image.", e);
            redirectAttributes.addFlashAttribute("uploadSuccess", false);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/images/{id}/setinfo")
    public ResponseEntity setInfo(@PathVariable Long id, @RequestParam String info) {
        imageService.setInfoForImage(id, info);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/images/{id}/delete")
    public ResponseEntity deleteImage(@PathVariable Long id) {
        storageService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/news")
    public String adminNews(Model model) {
        model.addAttribute("allArticles", newsService.getAll());
        return "adminNews";
    }

    @PostMapping("/admin/news/{id}/edit")
    public ResponseEntity editNews(@PathVariable Long id, NewsArticle editedArticle) {
        newsService.editArticle(id, editedArticle);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/news/{id}/delete")
    public ResponseEntity deleteNews(@PathVariable Long id) {
        newsService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
