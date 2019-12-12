package de.nikos410.ikl.welcome.service.impl;

import de.nikos410.ikl.welcome.database.SettingsRepository;
import de.nikos410.ikl.welcome.model.Settings;
import de.nikos410.ikl.welcome.service.SettingsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsServiceImpl(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public Settings getSettings() {
        final List<Settings> foundSettings = settingsRepository.findAll();
        if (foundSettings.isEmpty()) {
            return this.createInitialSettings();
        } else {
            return foundSettings.get(0);
        }
    }

    private synchronized Settings createInitialSettings() {
        final List<Settings> foundSettings = settingsRepository.findAll();
        if (foundSettings.isEmpty()) {
            return settingsRepository.saveAndFlush(new Settings());
        } else {
            return foundSettings.get(0);
        }
    }

    @Override
    public Settings saveSettings(Settings settings) {
        return settingsRepository.saveAndFlush(settings);
    }
}
