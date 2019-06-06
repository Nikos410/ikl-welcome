package de.nikos410.ikl.welcome.service;

import de.nikos410.ikl.welcome.model.Image;

import java.util.List;

public interface ImageService {
    Image getNextImage();

    String getInfoForImage(String fileName);

    void setInfoForImage(Long id, String info);

    List<Image> findAll();
}
