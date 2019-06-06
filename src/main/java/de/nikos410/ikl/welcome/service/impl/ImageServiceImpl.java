package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.database.ImageRepository;
import de.nikos410.ikl.welcome.model.Image;
import de.nikos410.ikl.welcome.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Random RANDOM = new Random();

    private ImageRepository imageRepository;

    private Image currentImage;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * This implementation returns a random image from the images directory
     *
     * @return The file encoded as base64
     */
    @Override
    public Image getNextImage() {
        return getRandomImage();
    }

    @Override
    public String getInfoForImage(String fileName) {
        final Image result = imageRepository.findOneByFile(fileName);
        return result == null ? null : result.getInfo();
    }

    private Image getRandomImage() {
        final List<Image> allImages = imageRepository.findAll();
        Image nextImage = null;

        if (allImages.isEmpty()) {
            return null;
        } else if (allImages.size() == 1) {
            nextImage = allImages.get(0);
        } else {
            while (nextImage == null || nextImage.equals(this.currentImage)) {
                nextImage = allImages.get(RANDOM.nextInt(allImages.size()));
            }
        }

        currentImage = nextImage;
        return nextImage;
    }
}
