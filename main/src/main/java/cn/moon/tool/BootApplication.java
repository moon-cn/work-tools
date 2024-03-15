package cn.moon.tool;


import cn.hutool.core.util.ClassUtil;
import cn.moon.Constants;
import cn.moon.WorkTool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.*;

public class BootApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 创建主窗口
        JFrame frame = new JFrame(Constants.APP_NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel();

        for (WorkTool tool : tools()) {
            JButton btn = new JButton(tool.getName());

            btn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tool.show();
                }
            });

            panel.add(btn);
        }



        // 将面板添加到窗口
        frame.getContentPane().add(panel);

        // 设置窗口大小并显示
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static List<WorkTool> tools() throws InstantiationException, IllegalAccessException {
        Set<Class<?>> clsList = ClassUtil.scanPackageBySuper(Constants.BASE_PACKAGE, WorkTool.class);

        List<WorkTool> list = new ArrayList<>();
        for (Class<?> cls : clsList) {
            WorkTool o = (WorkTool) cls.newInstance();
            list.add(o);
        }
        return list;
    }


}
