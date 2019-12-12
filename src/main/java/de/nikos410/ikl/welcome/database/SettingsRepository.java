package de.nikos410.ikl.welcome.database;

import de.nikos410.ikl.welcome.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {
    Settings findOneById(long id);
}
