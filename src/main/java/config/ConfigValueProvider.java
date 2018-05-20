package config;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigValueProvider {

    private static ResourceBundle spriteLocations = ResourceBundle.getBundle("spritepath");

    public static final String LEVEL1 = spriteLocations.getString("level1");

    public static String getSpritesLocation(String spriteName) {
        try {
            return spriteLocations.getString(spriteName.toLowerCase());
        } catch (MissingResourceException e) {
            e.printStackTrace();
            return null;
        }
    }

}
