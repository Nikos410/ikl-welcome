package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.database.ImageRepository;
import de.nikos410.ikl.welcome.exception.StorageException;
import de.nikos410.ikl.welcome.model.Image;
import de.nikos410.ikl.welcome.service.StorageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageServiceImpl implements StorageService {
    private static final Logger LOG = LoggerFactory.getLogger(StorageServiceImpl.class);
    private static final Path IMAGES_ROOT = Paths.get("images");

    private final ImageRepository imageRepository;

    public StorageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void init() {
        // TODO: Check if all files have an entry in database
        try {
            Files.createDirectories(IMAGES_ROOT);
        } catch (IOException e) {
            throw new StorageException("Could not initialize images directory.", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        final String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory: "
                                + filename);
            }
            final Path newPath = IMAGES_ROOT.resolve(filename);

            Files.copy(file.getInputStream(), newPath,
                    StandardCopyOption.REPLACE_EXISTING);

            final Image newImage = new Image();
            newImage.setFile(IMAGES_ROOT.relativize(newPath).toString());
            imageRepository.save(newImage);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public List<Path> findAll() {
        try {
            return Files.walk(IMAGES_ROOT, 1)
                    .filter(path -> !path.equals(IMAGES_ROOT))
                    .filter(Files::isRegularFile)
                    .map(IMAGES_ROOT::relativize)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files.", e);
        }
    }

    @Override
    public void deleteOne(final Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Could not delete image " + path, e);
        }
    }

    @Override
    public String getImageAsBase64(final Path image) {
        try {
            final byte[] binary = Files.readAllBytes(IMAGES_ROOT.resolve(image));
            final byte[] base64 = Base64.encodeBase64(binary);
            return new String(base64);
        } catch (IOException e) {
            throw new StorageException("Could not read image " + image, e);
        }
    }

    @Override
    public Resource getImageAsResource(Path image) {
        try {
            Resource resource = new UrlResource(IMAGES_ROOT.resolve(image).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file " + image);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file " + image, e);
        }
    }
}


