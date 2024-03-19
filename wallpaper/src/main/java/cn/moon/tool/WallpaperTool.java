package cn.moon.tool;


import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpUtil;
import cn.moon.WorkTool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;

@Slf4j
public class WallpaperTool implements WorkTool {
    @Override
    public String getName() {
        return "壁纸";
    }

    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
        Integer i = RandomUtil.randomInt(1, 105);
        String url = "http://moon.soulsoup.cn/wallpaper/" + i + ".jpg";


        File destFile = download(url);
        if(destFile != null){
            WallpaperChanger.setWallpaper(destFile);
            log.info("设置壁纸 {}", destFile.getAbsoluteFile());
        }
    }

    public static File download(String path) {
        log.info("下载壁纸 {}", path);
        File destFile = new File("月神的壁纸", FileNameUtil.getName(path));

        if (destFile.exists()) {
            log.info("壁纸下载过，不再下载");
            return destFile;
        }

        try {
            HttpUtil.downloadFile(path, destFile);
        }catch (HttpException e){
            log.error(e.getMessage());
            return null;
        }

        return destFile;
    }
}
