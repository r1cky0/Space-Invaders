package gui.main;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;



public class DisplayModeManager {

    /**
     * Questa funzione individua fra le possibili risoluzioni dello schermo
     * quella più grande e che abbia frequencyRate di almeno 60Hz
     *
     * @param widthRatio Horizontal Ratio
     * @param heightRatio Vertical Ratio
     * @return bestDisplayMode
     */
    public static DisplayMode getBiggestWithRatio(int widthRatio, int heightRatio) throws LWJGLException {
        DisplayMode bestMode = new DisplayMode(1000, 800);
        DisplayMode[] modes = Display.getAvailableDisplayModes();

        for (DisplayMode mode : modes) {
            if (mode.getWidth() > bestMode.getWidth()) {
                if ((mode.getWidth() * heightRatio) / widthRatio == mode.getHeight() && mode.getFrequency() >= 60) {
                    bestMode = mode;
                }
            }
        }
        return bestMode;
    }
}
