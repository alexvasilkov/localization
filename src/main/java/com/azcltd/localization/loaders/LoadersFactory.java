package com.azcltd.localization.loaders;

import com.azcltd.localization.utils.LocalizationException;
import com.azcltd.localization.settings.Settings;

public class LoadersFactory {

    private static final String GOOGLE_PUBLIC = "google-public";
    private static final String GOOGLE_PRIVATE = "google-private";

    public static Loader createLoader(Settings settings) {
        Settings.checkNotEmpty(settings.source, Settings.PROP_SOURCE);

        if (GOOGLE_PUBLIC.equalsIgnoreCase(settings.source)) {
            return new GoogleSpreadsheetPublicLoader();
        } else if (GOOGLE_PRIVATE.equalsIgnoreCase(settings.source)) {
            return new GoogleSpreadsheetPrivateLoader();
        } else {
            throw new LocalizationException("Unknown source: " + settings.source +
                    ", only " + GOOGLE_PUBLIC + " and " + GOOGLE_PRIVATE + " are supported");
        }
    }

}
