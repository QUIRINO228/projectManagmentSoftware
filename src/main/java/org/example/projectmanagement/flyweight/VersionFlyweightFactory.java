package org.example.projectmanagement.flyweight;

import org.example.projectmanagement.models.Version;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VersionFlyweightFactory {
    private static final Map<String, Version> flyweights = new HashMap<>();

    public static Version getVersion(String versionName, String releaseDate, String executableFilePath) {
        String key = generateKey(versionName, releaseDate, executableFilePath);
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, Version.builder()
                    .versionName(versionName)
                    .releaseDate(LocalDate.parse(releaseDate))
                    .executableFilePath(executableFilePath)
                    .build());
        }
        return flyweights.get(key);
    }

    private static String generateKey(String versionName, String releaseDate, String executableFilePath) {
        return String.join(":", versionName, releaseDate, executableFilePath);
    }
}