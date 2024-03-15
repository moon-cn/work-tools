package cn.moon.tool;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

import java.io.IOException;

public class WallpaperChanger {
    public static void setWallpaper(String imagePath) {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(String.format("reg add \"hkcu\\control panel\\desktop\" /v wallpaper /d \"%s\" /f", imagePath));


            int SPI_SETDESKWALLPAPER = 0x14;
            int SPIF_UPDATEINIFILE = 0x01;
            int SPIF_SENDWININICHANGE = 0x02;
            MyUser32.INSTANCE.SystemParametersInfoA(SPI_SETDESKWALLPAPER, 0, null, SPIF_UPDATEINIFILE | SPIF_SENDWININICHANGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String imagePath = "C:\\dev\\1.jpg";
        setWallpaper(imagePath);
    }

    private interface MyUser32 extends StdCallLibrary {
        MyUser32 INSTANCE = Native.loadLibrary("user32", MyUser32.class);

        boolean SystemParametersInfoA(int uiAction, int uiParam, String fnm, int fWinIni);
    }
}
