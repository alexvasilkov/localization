package com.azcltd.localization;

import com.azcltd.localization.entries.LocalizationInfo;
import com.azcltd.localization.generators.Generator;
import com.azcltd.localization.generators.GeneratorsFactory;
import com.azcltd.localization.loaders.Loader;
import com.azcltd.localization.loaders.LoadersFactory;
import com.azcltd.localization.settings.Settings;
import com.azcltd.localization.settings.SettingsReader;
import com.azcltd.localization.utils.LocalizationException;
import com.azcltd.localization.utils.Parser;

public class Localization {

    private static final String DEFAULT_CONFIG = "localization.properties";

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            generateLocalization(args);
            double time = (System.currentTimeMillis() - start) / 1000D;
            System.out.println(String.format("\nLocalization generated in %.1f seconds", time));
        } catch (LocalizationException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private static void generateLocalization(String[] args) throws Exception {
        // Loading settings
        Settings settings = new Settings();
        SettingsReader.readFromFile(settings, DEFAULT_CONFIG, true);
        SettingsReader.readFromCommandLine(settings, args);

        // Loading data
        Loader loader = LoadersFactory.createLoader(settings);
        String[][] cells = loader.loadCells(settings);
        if (cells == null)
            throw new LocalizationException("Loaded cells are null, check "
                    + loader.getClass().getSimpleName() + " logic");

        // Parsing data
        LocalizationInfo info = new Parser().parse(cells);

        // Genrating localization
        Generator generator = GeneratorsFactory.createGenerator(settings);
        generator.generate(info, settings);
    }


}
