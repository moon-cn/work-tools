package cn.moon.tool;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.http.HttpUtil;
import cn.moon.Constants;
import cn.moon.WorkTool;
import cn.moon.lang.json.JsonTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {

    JTextArea textArea;

    public MainFrame() {
        setTitle(Constants.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel menuPanel = new JPanel();

        for (WorkTool tool : scanTools()) {
            JButton btn = new JButton(tool.getName());
            btn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textArea.setText("-----------" + tool.getName() + "------------\n");
                    tool.onToolBtnClick();
                }
            });

            menuPanel.add(btn);
        }
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(menuPanel, BorderLayout.NORTH);


        // 显示
        {
            textArea = new JTextArea();
            textArea.setEditable(false);
            getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

            // 重定向System.out和System.err
            PrintStream printStream = new PrintStream(new ConsoleToUIStream(textArea));
            System.setOut(printStream);
            System.setErr(printStream);
        }

        this.welcome();
    }

    private java.util.List<WorkTool> scanTools() {
        Set<Class<?>> clsList = ClassUtil.scanPackageBySuper(Constants.BASE_PACKAGE, WorkTool.class);

        List<WorkTool> list = new ArrayList<>();
        for (Class<?> cls : clsList) {

            try {
                WorkTool o = (WorkTool) cls.newInstance();
                list.add(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        list.sort(Comparator.comparing(WorkTool::getName));
        return list;
    }

    private void welcome() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String info = HttpUtil.get("https://news.topurl.cn/api?count=20");

                Map<String, Object> result = JsonTool.jsonToMapQuietly(info);
                if ((Integer) result.get("code") == 200) {
                    Map<String, Object> data = (Map<String, Object>) result.get("data");

                    { // 句子
                        Map<String, Object> sentenceData = (Map<String, Object>) data.get("sentence");
                        String sentence = (String) sentenceData.get("sentence");
                        String author = (String) sentenceData.get("author");

                        setTitle(Constants.APP_NAME + "  " + sentence + " —— " + author);
                    }

                    {
                        // 诗词
                        System.out.println("------------------诗词--------------");
                        Map<String, Object> poem = (Map<String, Object>) data.get("poem");
                        String title = (String) poem.get("title");
                        String author = (String) poem.get("author");
                        List<String> content = (List<String>) poem.get("content");
                        System.out.println(title);
                        System.out.println(author);
                        System.out.println();
                        for (String s : content) {
                            System.out.println(s);
                        }
                    }

                    {
                        System.out.println();
                        System.out.println();
                        System.out.println("------------------新闻--------------");

                        List<Map<String, Object>> newsList = (List<Map<String, Object>>) data.get("newsList");

                        for (int i = 0; i < newsList.size(); i++) {
                            Map<String, Object> news = newsList.get(i);
                            System.out.printf("%02d. %s  %s%n", i + 1, news.get("category"), news.get("title"));
                        }
                    }


                }
            }
        });

    }


}