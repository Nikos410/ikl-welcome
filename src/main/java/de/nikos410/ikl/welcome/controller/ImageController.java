package de.nikos410.ikl.welcome.controller;

import de.nikos410.ikl.welcome.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;

@Controller
@RequestMapping("/images")
public class ImageController {
    private final StorageService storageService;

    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveImage(@PathVariable String filename) {
        Resource file = storageService.getImageAsResource(Paths.get(filename));
        final String extension = filename.substring(filename.lastIndexOf(".") + 1);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf("image/" + extension))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/{filename:.+}/delete")
    public void deleteImage(@PathVariable String filename) {
        storageService.deleteOne(Paths.get(filename));
    }
}
