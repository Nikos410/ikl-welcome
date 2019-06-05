package de.nikos410.ikl.welcome.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void init();

    Path store(MultipartFile file);

    List<Path> findAll();

    void deleteOne(Path image);

    String getImageAsBase64(Path image);

    Resource getImageAsResource(Path image);
}