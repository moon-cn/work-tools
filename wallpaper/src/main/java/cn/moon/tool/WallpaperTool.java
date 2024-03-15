package cn.moon.tool;


import cn.moon.WorkTool;
import com.sun.corba.se.spi.orbutil.threadpool.Work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WallpaperTool implements WorkTool {
    @Override
    public String getName() {
        return "壁纸";
    }

    @Override
    public void show() {
        JFrame frame = new JFrame("壁纸");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
// http://www.netbian.com/

        // 创建面板
        JPanel panel = new JPanel();

        JButton button = new JButton("随机壁纸");
        panel.add(button);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setWallpaper();
            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    public void setWallpaper(){
        // http://www.netbian.com/down.php?id=33359&type=1
        String imagePath = "C:\\dev\\2.jpg";

        WallpaperChanger.setWallpaper(imagePath);

    }
}
