package config;

import java.awt.*;

public class Config {

    static GraphicsDevice gd;
    static int width;
    static int height;

    public static boolean useViewportDimensions = true;

    public static int V_WIDTH = 1200;
    public static int V_HEIGHT = 800;
    public static int VIEWPORT_WIDTH = useViewportDimensions ? 312 : V_WIDTH;
    public static int VIEWPORT_HEIGHT = useViewportDimensions ? 192 : V_WIDTH;


    public static void getConfig() {
        try {
            gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
            V_WIDTH = width;
            V_HEIGHT = height;
            System.out.println(width + " " + height);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }
}