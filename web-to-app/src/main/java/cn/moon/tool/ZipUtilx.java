package cn.moon.tool;

import cn.hutool.core.util.ZipUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ZipUtilx {

    public static File unzip(File sourceFile, File unzipDir) {
        File unzip = ZipUtil.unzip(sourceFile, unzipDir,StandardCharsets.UTF_8);

        return unzip;
    }


}
