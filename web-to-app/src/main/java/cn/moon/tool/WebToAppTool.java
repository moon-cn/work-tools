package cn.moon.tool;

import cn.moon.WorkTool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebToAppTool implements WorkTool {
    @Override
    public String getName() {
        return "网页转应用";
    }

    @Override
    public void onToolBtnClick() {
      log.info("支持转Android， windows应用");
    }
}
