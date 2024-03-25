package cn.moon.tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.moon.WorkTool;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Files;


@Slf4j
public class WebToAppTool implements WorkTool {

    public static final String URL = "https://github.com/moon-cn/template-web-to-exe/archive/refs/tags/v0.0.1.zip";

    @Override
    public String getName() {
        return "网页转 EXE";
    }

    JTextArea urlTextArea;
    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
         urlTextArea = new JTextArea("https://www.douban.com");
        urlTextArea.setColumns(50);
        wrapPanel.add(urlTextArea);

        JButton btn2 = new JButton("生成 exe（单文件,C语言）");

        btn2.addActionListener(this::genExe2);

        wrapPanel.add(btn2);

        System.out.println("需提前安装好c++编译环境， MinGW-w64");
    }

    @SneakyThrows
    private void genExe2(ActionEvent actionEvent) {
        File dir = new File("temp");
        File file = new File(dir, "webview.zip");
        File unzipDir = new File(dir, FilenameUtils.getBaseName(file.getName()));

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (!file.exists()) {
            System.out.println("下载模板到文件夹" + URL);
            HttpUtil.download(URL, Files.newOutputStream(file.toPath()), true);
            log.info("下载模板完成");

            ZipUtilx.unzip(file,unzipDir);
            log.info("解压完成");

            Thread.sleep(1000);
        }


        File src = unzipDir.listFiles()[0];

        Assert.state(src.exists(), "目录不存在");
        System.out.println("源码目录 " + src.getAbsoluteFile());


        File main = new File(src, "main.cc");
        String code = FileUtil.readUtf8String(main);
        code = code.replace("https://baidu.com", urlTextArea.getText());
        System.out.println(code);
        FileUtil.writeUtf8String(code,main);


        String command = "g++ main.cc -std=c++14 -mwindows -Ilibs/webview -Ilibs/webview2/build/native/include -ladvapi32 -lole32 -lshell32 -lshlwapi -luser32 -lversion -o app.exe";
        command = "cmd /c " + command;

        System.out.println("编译：" + command);
        exec(src.getAbsoluteFile(), StrUtil.splitToArray(command, " "));

        System.out.println("编译完成");
        System.out.println("文件地址为" + new File(src, "app.exe").getAbsoluteFile());
    }


    private static void exec( File file,String... command) throws IOException {
        Process process = RuntimeUtil.exec(null, file, command);

        String result = RuntimeUtil.getResult(process);
        System.out.println(result);
        String errorResult = RuntimeUtil.getErrorResult(process);
        System.out.println(errorResult);
    }

}
