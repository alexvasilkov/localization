package com.azcltd.localization.loaders;

import com.azcltd.localization.utils.LocalizationException;
import com.azcltd.localization.settings.Settings;
import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.*;

import java.net.URL;

public abstract class GoogleSpreadsheetAbstractLoader implements Loader {

    protected static final String APP_NAME = "Localization";

    protected String[][] loadCells(SpreadsheetService service, String visibility, Settings settings) throws Exception {
        // Check required settings
        Settings.checkNotEmpty(settings.googleSpreadsheetKey, Settings.PROP_GOOGLE_SPREADSHEET_KEY);
        Settings.checkNotEmpty(settings.googleWorksheetName, Settings.PROP_GOOGLE_WORKSHEET_NAME);

        URL worksheetFeedUrl = FeedURLFactory.getDefault().getWorksheetFeedUrl(
                settings.googleSpreadsheetKey, visibility, "values");
        WorksheetFeed feed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);

        // Searching for worksheet with given name in the spreadsheet
        WorksheetEntry worksheet = null;

        for (WorksheetEntry w : feed.getEntries()) {
            if (settings.googleWorksheetName.equalsIgnoreCase(w.getTitle().getPlainText())) {
                worksheet = w;
                break;
            }
        }

        if (worksheet == null)
            throw new LocalizationException("Worksheet with name " + settings.googleWorksheetName + " was not found");

        // Loading all cells of the worksheet
        CellFeed cellFeed = service.getFeed(worksheet.getCellFeedUrl(), CellFeed.class);

        int rows = cellFeed.getRowCount();
        int cols = cellFeed.getColCount();

        String[][] sheet = new String[rows][cols];

        for (CellEntry cell : cellFeed.getEntries()) {
            Cell c = cell.getCell();
            sheet[c.getRow() - 1][c.getCol() - 1] = c.getValue();
        }

        return sheet;
    }

}
