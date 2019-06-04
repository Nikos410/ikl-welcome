package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.service.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService {
    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    private static final Path IMAGES_DIRECTORY = Paths.get("images/");

    private static final Random RANDOM = new Random();

    /**
     * This implementation returns a random image from the images directory
     *
     * @return The file encoded as base64
     */
    @Override
    public String getNextImageBase64() {
        try (Stream<Path> walk = Files.walk(IMAGES_DIRECTORY)) {
            final List<Path> allFiles = walk
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            if (allFiles.isEmpty()) {
                // TODO: Add placeholder image as resource
                return null;
            }
            else {
                return getBase64ForImage(allFiles.get(RANDOM.nextInt(allFiles.size())));
            }
        } catch (IOException e) {
            LOG.error("Could not read image directory contents.", e);
            return null;
        }
    }

    private String getBase64ForImage(final Path image) {
        try {
            final byte[] binary = Files.readAllBytes(image);
            final byte[] base64 = Base64.encodeBase64(binary);
            return new String(base64);
        } catch (IOException e) {
            LOG.error("Could not read image: " + image, e);
            return null;
        }
    }
}
