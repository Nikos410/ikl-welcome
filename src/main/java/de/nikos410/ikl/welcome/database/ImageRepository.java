package de.nikos410.ikl.welcome.database;

import de.nikos410.ikl.welcome.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findOneById(Long id);

    Image findOneByFile(String file);
}
