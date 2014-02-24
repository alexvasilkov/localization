package com.azcltd.localization.generators;

import com.azcltd.localization.entries.LocalizationInfo;
import com.azcltd.localization.settings.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class Generator {

    public static final String GENERATOR_COMMENT = "This file was generated with ..., do not modify";

    public abstract void generate(LocalizationInfo info, Settings settings) throws Exception;

    protected File prepareFile(Settings settings, String dir, String fileName) {
        Settings.checkNotEmpty(settings.outputDir, Settings.PROP_OUTPUT_DIR);

        File dirFile = new File(settings.outputDir, dir);
        dirFile.mkdirs();

        return new File(dirFile, fileName);
    }

    protected void writeFile(File file, String content) throws IOException {
        OutputStreamWriter writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            writer.write(content);
        } finally {
            if (writer != null) writer.close();
        }
    }

}
