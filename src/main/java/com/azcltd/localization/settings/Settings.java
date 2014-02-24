package com.azcltd.localization.settings;

import com.azcltd.localization.utils.LocalizationException;

public class Settings {

    public static final String PROP_TARGET = "target";
    public static final String PROP_DEFAULT_LANG = "default-lang";
    public static final String PROP_SOURCE = "source";
    public static final String PROP_OUTPUT_DIR = "output";
    public static final String PROP_GOOGLE_SPREADSHEET_KEY = "google-spreadsheet-key";
    public static final String PROP_GOOGLE_WORKSHEET_NAME = "google-worksheet-name";
    public static final String PROP_GOOGLE_LOGIN = "google-login";
    public static final String PROP_GOOGLE_PASSWORD = "google-password";

    public String target;
    public String defaultLang;
    public String source;
    public String outputDir;
    public String googleSpreadsheetKey;
    public String googleWorksheetName;
    public String googleLogin;
    public String googlePassword;

    public static void checkNotEmpty(Object property, String propertyName) {
        if (property == null || (property instanceof String && ((String) property).length() == 0))
            throw new LocalizationException("Property \"" + propertyName + "\" cannot be empty");
    }

}
