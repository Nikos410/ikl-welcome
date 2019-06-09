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

    private long currentImage = -1;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * This implementation returns a random image from the images directory
     *
     * @return The file encoded as base64
     */
    @Override
    public Image getNextImage(Long lastId) {
        final List<Image> allImages = imageRepository.findAll();

        // Return fallback image if no images can be found
        if (allImages.isEmpty()) {
            return null;
        }

        // Return the image with the next highest ID
        for (Image image : allImages) {
            if (image.getId() > lastId) {
                return image;
            }
        }

        // If no image with a higher ID can be found, return the first image
        return allImages.get(0);
    }

    @Override
    public String getInfoForImage(String fileName) {
        final Image result = imageRepository.findOneByFile(fileName);
        return result == null ? null : result.getInfo();
    }

    @Override
    public void setInfoForImage(Long id, String info) {
        final Image result = imageRepository.findOneById(id);
        result.setInfo(info);
        imageRepository.save(result);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
