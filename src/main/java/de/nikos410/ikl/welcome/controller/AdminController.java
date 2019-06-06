package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.exception.StorageException;
import de.nikos410.ikl.welcome.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Paths;

@Controller
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final StorageService storageService;

    public AdminController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("allImages", storageService.findAll());
        return "admin";
    }

    @PostMapping("/admin/upload")
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

    @PostMapping("/images/{filename:.+}/delete")
    public void deleteImage(@PathVariable String filename) {
        storageService.deleteOne(Paths.get(filename));
    }
}
