package cn.moon.tool;


import cn.hutool.core.util.ClassUtil;
import cn.moon.Constants;
import cn.moon.WorkTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class BootApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        MainFrame frame = new MainFrame();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        // 控制台输出示例
        System.out.println("系统启动");
    }


}
