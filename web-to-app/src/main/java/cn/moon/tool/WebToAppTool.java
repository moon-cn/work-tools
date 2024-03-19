package cn.moon.tool;

import cn.moon.WorkTool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class WebToAppTool implements WorkTool {
    @Override
    public String getName() {
        return "网页转应用";
    }

    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
        log.info("支持转Android， windows应用");

        JTextArea text = new JTextArea("https://");
        text.setColumns(50);
        wrapPanel.add(text);

        JButton btn = new JButton("生成");

        wrapPanel.add(btn);
    }


    private void gen(){

    }
}
