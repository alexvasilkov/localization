package com.azcltd.localization.utils;

import com.azcltd.localization.entries.LocalizationInfo;
import com.azcltd.localization.entries.LocalizationStructure;
import com.azcltd.localization.entries.LocalizationTable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Parser {

    private static final String KEY = "key";
    private static final String DESCRIPTION = "description";
    private static final String LANG_PREFFIX = "lang:";
    private static final String SECTION_PREFFIX = "#";

    private int colKey = -1, colDescription = -1;
    private final Map<Locale, Integer> mapLanguages = new HashMap<Locale, Integer>();

    public LocalizationInfo parse(String[][] cells) {
        if (cells.length == 0) throw new LocalizationException("No localization records found (empty cells array)");
        if (cells[0].length == 0) throw new LocalizationException("Too few columns");

        parseHeader(cells[0]);

        LocalizationInfo info = new LocalizationInfo(new LocalizationStructure(),
                new LocalizationTable(mapLanguages.keySet()));

        String[] row;
        String key, description, value;

        for (int r = 1; r < cells.length; r++) {
            row = cells[r];

            if (row[0] != null && row[0].startsWith(SECTION_PREFFIX)) {
                value = row[0].substring(SECTION_PREFFIX.length());
                info.structure.addSection(value);
            } else {
                key = row[colKey];
                description = row[colDescription];

                if (key != null && key.length() > 0) {
                    info.structure.addKey(key, description);

                    for (Locale lang : mapLanguages.keySet()) {
                        value = row[mapLanguages.get(lang)];
                        if (value == null || value.length() == 0) {
                            System.out.println("Missing \"" + lang + "\" localization for key: " + key);
                        }
                        info.localization.addValue(key, value, lang);
                    }
                }
            }
        }

        return info;
    }

    private void parseHeader(String[] header) {
        String name;
        for (int i = 0; i < header.length; i++) {
            name = header[i];
            if (name == null || name.length() == 0) continue;
            name = name.trim();

            name = name.toLowerCase(Locale.ENGLISH);

            if (KEY.equals(name)) {
                colKey = i;
            } else if (DESCRIPTION.equals(name)) {
                colDescription = i;
            } else {
                boolean prefixUsed = false;
                String langTag = name;
                if (langTag.startsWith(LANG_PREFFIX)) {
                    prefixUsed = true;
                    langTag = langTag.substring(LANG_PREFFIX.length()).trim();
                }

                Locale locale = null;
                try {
                    locale = LocaleUtils.getLocale(langTag);
                } catch (LocalizationException e) {
                    if (prefixUsed) throw e;
                }
                if (locale == null) continue;

                System.out.println("Language detected: " + locale.toString());
                mapLanguages.put(locale, i);
            }
        }

        checkColumn(colKey, KEY);
        checkColumn(colDescription, DESCRIPTION);
        if (mapLanguages.size() == 0) throw new LocalizationException("No localization columns were found");
    }

    private static void checkColumn(int col, String name) {
        if (col == -1) throw new LocalizationException("Column with name \"" + name + "\" was not found");
    }

}
