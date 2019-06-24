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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String adminImages(Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) Integer page) {
        if (page == null) {
            redirectAttributes.addAttribute("page", 0);
            return "redirect:/admin/images";
        }

        model.addAttribute("images", imageService.getPage(page));
        model.addAttribute("currentPage", page);
        model.addAttribute("pageCount", imageService.getPageCount());
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
        model.addAttribute("newArticle", new NewsArticle());
        return "adminNews";
    }

    @PostMapping("/news/new")
    public String newArticle(NewsArticle newArticle) {
        newsService.addArticle(newArticle);
        return "redirect:/admin/news";
    }

    @PostMapping("/news/{id}/edit")
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
