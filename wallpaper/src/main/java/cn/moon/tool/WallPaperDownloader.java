package cn.moon.tool;

import cn.hutool.http.HttpUtil;

public class WallPaperDownloader {

    public static void index(){
        String s = HttpUtil.get("https://bz.zzzmh.cn/index");
        System.out.println(s);

    }

    public static void main(String[] args) {
        index();
    }
}
