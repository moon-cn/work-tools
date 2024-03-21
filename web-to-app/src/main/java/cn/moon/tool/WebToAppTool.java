package cn.moon.tool;

import cn.moon.WorkTool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * https://learn.microsoft.com/zh-cn/dotnet/core/deploying/single-file/overview?tabs=cli

    <PublishSingleFile>true</PublishSingleFile>
     <SelfContained>false</SelfContained>
     <DebugType>embedded</DebugType>
    <RuntimeIdentifier>win-x64</RuntimeIdentifier>
 *

 using System;
 using System.Windows.Forms;

 namespace WebBrowserExample
 {
 public partial class MainForm : Form
 {
 private WebBrowser webBrowser;

 public MainForm()
 {
 InitializeComponent();
 }

 private void MainForm_Load(object sender, EventArgs e)
 {
 webBrowser = new WebBrowser();

 webBrowser.Dock = DockStyle.Fill;

 Controls.Add(webBrowser);
 webBrowser.Navigate("https://www.example.com");
 }
 }
 }

 新思路： 使用C# 写一个浏览器， 将URL 放到资源 文件中，如xxx.bitmap， 生成exe。 然后用resource hanker等工具，动态修改xxx,bitmap的内容


 *
 */
@Slf4j
public class WebToAppTool implements WorkTool {
    @Override
    public String getName() {
        return "网页转应用";
    }

    @Override
    public void onToolBtnClick(JPanel wrapPanel) {
        JTextArea text = new JTextArea("https://baidu.com");
        text.setColumns(50);
        wrapPanel.add(text);

        JButton btn = new JButton("生成 exe");

        btn.addActionListener(this::genExe);

        wrapPanel.add(btn);

        log.info("需预先安装 dotnet");
        log.info("下载地址：https://dotnet.microsoft.com/zh-cn/download/dotnet?cid=getdotnetcorecli");
    }

    private void genExe(ActionEvent actionEvent) {
        File file = new File("temp","dotnet");
        if(!file.exists()){
            file.mkdirs();
        }

        try {
        //    exec( "dotnet new winforms ", file);
            Thread.sleep(100);
            exec( "dotnet publish --configuration Release  ", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void exec(String command, File file) throws IOException {
        Process process = Runtime.getRuntime().exec(command, null, file);

// 获取进程的输入流
        InputStream inputStream = process.getInputStream();

        // 创建一个读取器来读取输入流
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");

        // 创建一个缓冲区读取字符
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 读取输出
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // 关闭相关的流
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }


}
