package com.azcltd.localization.utils;

import java.util.Locale;
import java.util.MissingResourceException;

public class LocaleUtils {

    public static Locale getLocale(String lang) {
        if (lang == null || lang.length() == 0) throw new LocalizationException("Language code cannot be empty");

        Locale locale;

        String[] parts = lang.split("_");
        switch (parts.length) {
            case 2:
                locale = new Locale(parts[0], parts[1]);
                break;
            case 1:
                locale = new Locale(parts[0]);
                break;
            default:
                throw new LocalizationException("Invalid language code: " + lang);
        }

        // Checking language code
        try {
            locale.getISO3Language();
        } catch (MissingResourceException e) {
            throw new LocalizationException("Invalid language code: " + locale.getLanguage());
        }

        // Checking country code
        try {
            locale.getISO3Country();
        } catch (MissingResourceException e) {
            throw new LocalizationException("Invalid country code: " + locale.getCountry());
        }

        return locale;
    }

}
