package cn.moon.tool;


import cn.hutool.http.HttpUtil;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;

import java.util.List;

public class BootApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // 设置主题
        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

        // 启动窗体
        MainFrame frame = new MainFrame();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }






}
