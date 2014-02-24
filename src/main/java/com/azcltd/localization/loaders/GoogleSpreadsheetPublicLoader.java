package com.azcltd.localization.loaders;

import com.azcltd.localization.settings.Settings;
import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class GoogleSpreadsheetPublicLoader extends GoogleSpreadsheetAbstractLoader {

    @Override
    public String[][] loadCells(Settings settings) throws Exception {
        return loadCells(new SpreadsheetService(APP_NAME), "public", settings);
    }

}
