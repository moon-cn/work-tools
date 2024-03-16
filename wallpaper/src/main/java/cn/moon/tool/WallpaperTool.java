package cn.moon.tool;


import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import cn.moon.WorkTool;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

@Slf4j
public class WallpaperTool implements WorkTool {
    @Override
    public String getName() {
        return "壁纸";
    }

    @Override
    public void onToolBtnClick() {
        Integer i = RandomUtil.randomInt(1, 5);
        String url = "http://moon.soulsoup.cn/wallpaper/" + i + ".jpg";


        File destFile = download(url);
        WallpaperChanger.setWallpaper(destFile);
        log.info("设置壁纸 {}", destFile.getAbsoluteFile());
    }

    public static File download(String path) {
        log.info("下载壁纸 {}", path);
        File destFile = new File("月神的壁纸", FileNameUtil.getName(path));

        if (destFile.exists()) {
            log.info("壁纸下载过，不再下载");
            return destFile;
        }

        HttpUtil.downloadFile(path, destFile);
        return destFile;
    }
}
