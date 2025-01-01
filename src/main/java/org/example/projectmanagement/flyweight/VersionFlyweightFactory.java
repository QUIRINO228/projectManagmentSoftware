package org.example.projectmanagement.flyweight;

import org.example.projectmanagement.models.Version;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class VersionFlyweightFactory {
    private static final Map<String, Version> flyweights = new HashMap<>();

    public static Version getVersion(String executableFilePath) {
        String key = generateKey(executableFilePath);
        if (!flyweights.containsKey(key)) {
            flyweights.put(key, Version.builder()
                    .executableFilePath(executableFilePath)
                    .build());
        }
        return flyweights.get(key);
    }

    private static String generateKey(String executableFilePath) {
        return String.join(":", executableFilePath);
    }
}