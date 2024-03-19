package cn.moon.tool.pwd;

import cn.hutool.core.util.RandomUtil;
import cn.moon.WorkTool;

import javax.swing.*;

public class PwdTool implements WorkTool {
    @Override
    public String getName() {
        return "密码生成";
    }

    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
        for (int i = 0; i < 10; i++){
            String s = RandomUtil.randomString(16);
            System.out.println(s);
        }

    }
}
