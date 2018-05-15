package config;

import java.util.Properties;
import java.util.ResourceBundle;

public class PathProvider {

    static Properties configFile = new Properties();
    private static ResourceBundle rb = ResourceBundle.getBundle("project");

    public static final String SHIP = rb.getString("path_to_entities");
    public static final String LEVEL1 = rb.getString("path_to_level1");

}
