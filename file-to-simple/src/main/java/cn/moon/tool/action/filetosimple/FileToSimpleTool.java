package cn.moon.tool.action.filetosimple;


import cn.moon.Tool;
import cn.moon.lang.web.Result;
import com.github.houbb.opencc4j.util.ZhConverterUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

// 繁体文件名转简体
public class FileToSimpleTool implements Tool {

    @Override
    public String getName() {
        return "繁体文件名转简体";
    }

    @Override
    public String getId() {
        return "file-to-simple";
    }

    @Override
    public void show() {
        JFrame newFrame = new JFrame("新窗体1");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // 创建面板
        JPanel panel = new JPanel();

        JButton button = new JButton("选择文件夹");
        panel.add(button);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只能选择文件夹

                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog((Component) e.getSource());
                // 判断用户是否点击了选择按钮
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 获取用户选择的文件夹
                    String selectedFolder = fileChooser.getSelectedFile().getAbsolutePath();

                    // 在控制台打印选择的文件夹路径
                    System.out.println("Selected folder: " + selectedFolder);

                    process(selectedFolder);
                }

            }
        });

        newFrame.add(panel);

        newFrame.setSize(200, 150);
        newFrame.setVisible(true);
    }


    public Result process(String path) {
        System.out.println("文件繁体转简体工具");

        File dir = new File(new File(path).getAbsolutePath());
        File[] files = dir.listFiles();

        convert(files);

        System.out.println("转换结束");

        return Result.ok().msg("转换结束");
    }

    private void convert(File[] files) {
        for (File f : files) {
            if (f.isDirectory()) {
                convert(f.listFiles());
            }

            String name = f.getName();
            String simple = ZhConverterUtil.toSimple(name);
            if (!name.equals(simple)) {
                System.out.println(name + " => " + simple);

                File dest = new File(f.getParentFile(), simple);
                f.renameTo(dest);
            }
        }
    }
}
