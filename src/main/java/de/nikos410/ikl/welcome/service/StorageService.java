package de.nikos410.ikl.welcome.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface StorageService {

    void init();

    void store(MultipartFile file);

    List<Path> findAllOnDisk();

    void deleteOne(Path image);

    void deleteOne(Long id);

    String getImageAsBase64(Path image);

    Resource getImageAsResource(Path image);
}