package com.azcltd.localization.generators;

import com.azcltd.localization.entries.LocalizationInfo;
import com.azcltd.localization.entries.LocalizationStructure;
import com.azcltd.localization.settings.Settings;
import com.azcltd.localization.utils.LocaleUtils;
import com.azcltd.localization.utils.LocalizationException;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class AndroidGenerator extends Generator {

    @Override
    public void generate(LocalizationInfo info, Settings settings) throws IOException {
        for (Locale lang : info.localization.getLanguages()) {
            generateLocalizationFile(info, lang, settings);
        }
    }

    private void generateLocalizationFile(LocalizationInfo info, Locale lang, Settings settings) throws IOException {
        Settings.checkNotEmpty(settings.defaultLang, Settings.PROP_DEFAULT_LANG);
        boolean isDefaultLang = lang.equals(LocaleUtils.getLocale(settings.defaultLang));

        String langName = lang.getLanguage();
        String langCountry = lang.getCountry();
        String langSuffix = langName + (langCountry == null || langCountry.length() == 0 ? "" : "-r" + langCountry);

        String dirName = "values" + (isDefaultLang ? "" : "-" + langSuffix);
        String fileName = "generated_strings.xml";
        File file = prepareFile(settings, dirName, fileName);
        System.out.println("Generating localization file: " + file.getAbsoluteFile());

        String content = prepare(info, lang, isDefaultLang);

        writeFile(file, content);
    }

    private static String prepare(LocalizationInfo info, Locale lang, boolean isDefaultLang) {
        StringBuilder result = new StringBuilder();
        result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n\n");
        result.append("<!-- " + GENERATOR_COMMENT + " -->\n\n");
        result.append("<resources>\n");

        String key, value, description;

        for (LocalizationStructure.Record record : info.structure.getRecords()) {
            switch (record.type) {
                case SECTION:
                    result.append("\n\t<!-- ").append(record.value).append(" -->");
                    break;
                case KEY:
                    key = record.value;
                    value = escapeValue(info.localization.getValue(key, lang));
                    description = record.description;

                    if (value == null || value.length() == 0) {
                        if (isDefaultLang) throw new LocalizationException("Missing value for default language (" +
                                lang + ") for key: " + key);
                        continue;
                    }

                    result.append("\t<string name=\"").append(key).append("\">").append(value).append("</string>");

                    if (description != null && description.length() > 0) {
                        result.append("  <!-- ").append(description).append(" -->");
                    }
                    break;
            }

            result.append('\n');
        }

        result.append("\n</resources>\n");

        return result.toString();
    }

    private static String escapeValue(String value) {
        if (value == null) return null;
        return value.replace("'", "\\'").replace("<", "&lt;").replace(">", "&gt;");
    }

}
