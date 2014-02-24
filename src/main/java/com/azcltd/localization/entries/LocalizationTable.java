package com.azcltd.localization.entries;

import com.azcltd.localization.utils.LocalizationException;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;

public class LocalizationTable {

    private final LinkedHashMap<Locale, Integer> languages;
    private final LinkedHashMap<String, String[]> values;

    public LocalizationTable(Collection<Locale> languages) {
        this.languages = new LinkedHashMap<Locale, Integer>();
        int i = 0;
        for (Locale lang: languages) {
            this.languages.put(lang, i++);
        }
        this.values = new LinkedHashMap<String, String[]>();
    }

    public void addValue(String key, String value, Locale language) {
        String[] keyValues = values.get(key);
        if (keyValues == null) {
            keyValues = new String[languages.size()];
            values.put(key, keyValues);
        }

        Integer langPos = languages.get(language);
        if (langPos == null)
            throw new LocalizationException("Trying to add localization for unknown language: \"" + language + "\"");

        keyValues[langPos] = value;
    }

    public Collection<Locale> getLanguages() {
        return languages.keySet();
    }

    public Collection<String> getKeys() {
        return values.keySet();
    }

    public String getValue(String key, Locale language) {
        String[] keyValues = values.get(key);
        if (keyValues == null)
            throw new LocalizationException("Localization for key \"" + key + "\" is not found");

        Integer langPos = languages.get(language);
        if (langPos == null)
            throw new LocalizationException("Localization for language \"" + language + "\" is not found");

        return keyValues[langPos];
    }

}
