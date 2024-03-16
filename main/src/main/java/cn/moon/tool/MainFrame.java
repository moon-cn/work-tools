package cn.moon.tool;

import cn.hutool.core.util.ClassUtil;
import cn.moon.Constants;
import cn.moon.WorkTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle(Constants.APP_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel toolPanel = new JPanel();

        for (WorkTool tool : tools()) {
            JButton btn = new JButton(tool.getName());
            btn.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tool.onToolBtnClick();
                }
            });

            toolPanel.add(btn);
        }
        getContentPane().add(toolPanel, BorderLayout.NORTH);


        // 显示
        {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);

            // 重定向System.out和System.err
            PrintStream  printStream = new PrintStream(new CustomOutputStream(textArea));
            System.setOut(printStream);
            System.setErr(printStream);

        }
    }

    private java.util.List<WorkTool> tools() {
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
        return list;
    }

    private static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        ByteBuffer bf = ByteBuffer.allocate(1024);

        @Override
        public void write(int b) throws IOException {
            bf.put((byte) b);

            if(b == '\n'){
                byte[] array = bf.array();
                textArea.append(new String(array, 0, bf.position()));
                textArea.setCaretPosition(textArea.getDocument().getLength());

                bf.clear();
            }



        }
    }


}