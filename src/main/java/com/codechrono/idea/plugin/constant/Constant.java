package com.codechrono.idea.plugin.constant;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;


public interface Constant {

    /**
     * User directory
     *  e.g. Windows OS :
     *      USER_HOME_PATH ==> C:\Users\Administrator(Your User Name)
     */
    String USER_HOME_PATH = System.getProperty("user.home");
    /**
     * Data directory of this plugin:
     * USER_HOME_PATH/.ideaCodechronosFile
     */
    Path PROJECT_DB_DIRECTORY_PATH = Paths.get(USER_HOME_PATH, ".ideaCodeChronoFile");
    /**
     * The image data of this plugin is stored here:
     * USER_HOME_PATH/.ideaCodechronosFile/codechrono_images
     */
    Path IMAGE_DIRECTORY_PATH = PROJECT_DB_DIRECTORY_PATH.resolve("codechrono_images");
    /**
     * Temporary image data of this plugin, stored here:
     * USER_HOME_PATH/.ideaCodechronosFile/codechrono_images_temp
     */
    Path TEMP_IMAGE_DIRECTORY_PATH = PROJECT_DB_DIRECTORY_PATH.resolve("codechrono_images_temp");
    /**
     * SQLite database file for this plugin:
     * USER_HOME_PATH/.ideaCodechronosFile/codechronos.db
     */
    Path DB_FILE_PATH = PROJECT_DB_DIRECTORY_PATH.resolve("codechrono.db");

    String EMAIL_GMAIL = "924066173@qq.com";


    /**
     * Is the language of this plugin Chinese?
     */
    Boolean languageIsZh = "zh".equals(Locale.getDefault().getLanguage());



}
