package com.azcltd.localization.loaders;

import com.azcltd.localization.settings.Settings;

public interface Loader {

    String[][] loadCells(Settings settings) throws Exception;

}
