package cn.moon.tool;

import cn.moon.WorkTool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class FtpTool implements WorkTool {
    @Override
    public String getName() {
        return "FTP局域网文件共享";
    }

    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
      log.info("其他用户可以通过浏览器或资源管理器");
    }
}
