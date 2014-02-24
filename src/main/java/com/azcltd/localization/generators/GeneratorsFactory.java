package com.azcltd.localization.generators;

import com.azcltd.localization.utils.LocalizationException;
import com.azcltd.localization.settings.Settings;

public class GeneratorsFactory {

    private static final String ANDROID = "android";
    private static final String IOS = "ios";

    public static Generator createGenerator(Settings settings) {
        Settings.checkNotEmpty(settings.target, Settings.PROP_TARGET);

        if (ANDROID.equalsIgnoreCase(settings.target)) {
            return new AndroidGenerator();
        } else if (IOS.equalsIgnoreCase(settings.target)) {
            return null; // TODO
        } else {
            throw new LocalizationException("Unknown target: " + settings.target +
                    ", only " + ANDROID + " and " + IOS + " are supported");
        }
    }

}
