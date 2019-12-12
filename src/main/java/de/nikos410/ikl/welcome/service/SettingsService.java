package de.nikos410.ikl.welcome.service;

import de.nikos410.ikl.welcome.model.Settings;

public interface SettingsService {
    Settings getSettings();

    Settings saveSettings(Settings settings);
}
