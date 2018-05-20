package config;

import java.awt.*;

public class Config {

    static GraphicsDevice gd;
    static int width;
    static int height;

    public static boolean useViewportDimensions = true;

    public static int V_WIDTH;
    public static int V_HEIGHT;
    public static int VIEWPORT_WIDTH;
    public static int VIEWPORT_HEIGHT;
    public static final int screenWidth = 400;
    public static final int screenHeight = 300;

    public static void getConfig() {
        try {
            gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
            V_WIDTH = width;
            V_HEIGHT = height;
            VIEWPORT_WIDTH = useViewportDimensions ? screenWidth : V_WIDTH;
            VIEWPORT_HEIGHT = useViewportDimensions ? screenHeight : V_HEIGHT;
            System.out.println(width + " " + height);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }
}