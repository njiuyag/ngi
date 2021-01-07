package cn.njiuyag.commandline.screw;

/**
 * @author hjx
 * @date 2021/1/5
 */
public class ScrewConfigKey {
    public static final String VERSION = "version";
    public static final String DESCRIPTION = "description";

    public static final String DATABASE_DRIVER_CLASS_NAME = "database.driver-class-name";
    public static final String DATABASE_DRIVER_CLASS_NAME_DEFAULT_VALUE = "com.mysql.cj.jdbc.Driver";
    public static final String DATABASE_URL = "database.url";
    public static final String DATABASE_USER_NAME = "database.username";
    public static final String DATABASE_PASSWORD = "database.password";

    public static final String OUTPUT_FILE_DIR = "output.file.dir";
    public static final String OUTPUT_FILE_TYPE = "output.file.type";
    public static final String OUTPUT_FILE_NAME = "output.file.name";

    public static final String IGNORE_TABLE_NAMES = "ignore.table-names";
    public static final String IGNORE_TABLE_PREFIXES = "ignore.table-prefixes";
    public static final String IGNORE_TABLE_SUFFIXES = "ignore.table-suffixes";

}
