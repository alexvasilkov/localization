package com.azcltd.localization.loaders;

import com.azcltd.localization.utils.LocalizationException;
import com.azcltd.localization.settings.Settings;
import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class GoogleSpreadsheetPrivateLoader extends GoogleSpreadsheetAbstractLoader {

    @Override
    public String[][] loadCells(Settings settings) throws Exception {
        SpreadsheetService service = new SpreadsheetService(APP_NAME);

        String login = settings.googleLogin;
        String password = settings.googlePassword;

        if (login == null || login.length() == 0) {
            if (System.console() == null) throw new LocalizationException("Can't read google login from console");

            System.console().printf("Google account login:\n");
            login = System.console().readLine();
        }

        if (password == null || password.length() == 0) {
            if (System.console() == null) throw new LocalizationException("Can't read google password from console");

            System.console().printf("Google account password:\n");
            password = new String(System.console().readPassword());
        }

        Settings.checkNotEmpty(login, Settings.PROP_GOOGLE_LOGIN);
        Settings.checkNotEmpty(password, Settings.PROP_GOOGLE_PASSWORD);

        service.setUserCredentials(login, password);

        return loadCells(service, "private", settings);
    }

}
