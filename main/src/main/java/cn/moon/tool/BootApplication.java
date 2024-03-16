package cn.moon.tool;


import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;

public class BootApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {



        LafManager.setTheme(new DarculaTheme());
        LafManager.install();

        MainFrame frame = new MainFrame();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        // 控制台输出示例
        System.out.println("系统启动");
    }


}
