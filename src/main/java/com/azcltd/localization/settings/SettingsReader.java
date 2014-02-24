package com.azcltd.localization.settings;

import com.azcltd.localization.utils.LocalizationException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SettingsReader {

    public static void readFromFile(Settings settings, String filePath, boolean ignoreIfNotFound) {
        Properties props = null;
        try {
            props = readPropertiesFile(filePath);
        } catch (FileNotFoundException e) {
            if (!ignoreIfNotFound) throw new LocalizationException("Settings file " + filePath + " was not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (props == null) return;

        Map<String, String> map = new HashMap<String, String>();
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }

        read(settings, map);
    }

    public static void readFromCommandLine(Settings settings, String[] args) {
        if (args == null) return;

        Map<String, String> map = new HashMap<String, String>();
        String key;
        for (int i = 0; i < args.length; i++) {
            key = args[i];
            if (key != null && key.length() > 0 && key.startsWith("--")) {
                map.put(key.substring(2), args[i + 1]);
            }
        }

        read(settings, map);

        String configFile = map.get("config");
        if (configFile != null && configFile.length() > 0)
            readFromFile(settings, configFile, false);
    }

    private static void read(Settings settings, Map<String, String> map) {
        settings.target = get(map, Settings.PROP_TARGET, settings.target);
        settings.defaultLang = get(map, Settings.PROP_DEFAULT_LANG, settings.defaultLang);
        settings.source = get(map, Settings.PROP_SOURCE, settings.source);
        settings.outputDir = get(map, Settings.PROP_OUTPUT_DIR, settings.outputDir);
        settings.googleSpreadsheetKey = get(map, Settings.PROP_GOOGLE_SPREADSHEET_KEY, settings.googleSpreadsheetKey);
        settings.googleWorksheetName = get(map, Settings.PROP_GOOGLE_WORKSHEET_NAME, settings.googleWorksheetName);
        settings.googleLogin = get(map, Settings.PROP_GOOGLE_LOGIN, settings.googleLogin);
        settings.googlePassword = get(map, Settings.PROP_GOOGLE_PASSWORD, settings.googlePassword);
    }

    private static String get(Map<String, String> map, String key, String oldValue) {
        String value = map.get(key);
        return value == null || value.length() == 0 ? oldValue : value;
    }

    private static Properties readPropertiesFile(String filePath) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            props.load(in);
            return props;
        } finally {
            if (in != null) in.close();
        }
    }

}
