public class Config {

    public static boolean useViewportDimensions = true;

    public static int V_WIDTH = 800;
    public static int V_HEIGHT = 480;
    public static int VIEWPORT_WIDTH = useViewportDimensions ? 312 : V_WIDTH;
    public static int VIEWPORT_HEIGHT = useViewportDimensions ? 192 : V_WIDTH;

}