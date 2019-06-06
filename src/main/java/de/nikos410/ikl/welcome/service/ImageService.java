package de.nikos410.ikl.welcome.service;

import de.nikos410.ikl.welcome.model.Image;

import java.nio.file.Path;

public interface ImageService {
    Image getNextImage();

    String getInfoForImage(String fileName);
}
