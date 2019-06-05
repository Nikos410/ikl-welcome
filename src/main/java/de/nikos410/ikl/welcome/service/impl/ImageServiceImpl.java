package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.service.ImageService;
import de.nikos410.ikl.welcome.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Random RANDOM = new Random();

    private final StorageService storageService;

    private Path currentImage;

    @Autowired
    public ImageServiceImpl(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * This implementation returns a random image from the images directory
     *
     * @return The file encoded as base64
     */
    @Override
    public String getNextImageBase64() {
        final Path nextImage = getRandomImage();

        currentImage = nextImage;
        return storageService.getImageAsBase64(nextImage);
    }

    private Path getRandomImage() {
        final List<Path> allFiles = storageService.findAll();
        Path nextImage = null;

        if (allFiles.isEmpty()) {
            // TODO: Add placeholder image as resource
        }
        else if (allFiles.size() == 1) {
            nextImage = allFiles.get(0);
        }
        else {
            while (nextImage == null || nextImage.equals(this.currentImage)) {
                nextImage = allFiles.get(RANDOM.nextInt(allFiles.size()));
            }
        }

        return nextImage;
    }
}