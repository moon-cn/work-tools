package cn.moon.tool;


import cn.moon.Tool;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class PDFToJPGTool implements Tool {

    @Override
    public String getName() {
        return "PDF转JPG";
    }

    @Override
    public void show() {
        JFrame frame = new JFrame("新窗体1");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // 创建面板
        JPanel panel = new JPanel();

        JButton button = new JButton("选择文件夹");
        panel.add(button);

        button.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().toLowerCase().endsWith(".pdf");
                    }

                    @Override
                    public String getDescription() {
                        return ".pdf";
                    }
                });

                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog((Component) e.getSource());
                // 判断用户是否点击了选择按钮
                if (result == JFileChooser.APPROVE_OPTION) {
                    // 获取用户选择的文件夹
                    String selectedFolder = fileChooser.getSelectedFile().getAbsolutePath();

                    // 在控制台打印选择的文件夹路径
                    System.out.println("Selected folder: " + selectedFolder);

                    handle(selectedFolder);
                }

            }
        });

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    /**
     * 经过测试,dpi为96,100,105,120,150,200中,105显示效果较为清晰,体积稳定,dpi越高图片体积越大,一般电脑显示分辨率为96
     */
    public static final float DEFAULT_DPI = 105;

    /**
     * 默认转换的图片格式为jpg
     */
    public static final String DEFAULT_FORMAT = "jpg";

    @SneakyThrows
    public  void handle(String path) {
        File file = new File(path);

        System.out.println("文件：" + file.getAbsolutePath());

        File target = new File(file.getParentFile(), "PDF转JPG结果目录");
        if (!target.exists()) {
            target.mkdir();
        }

        //加载pdf文件
        PDDocument doc = PDDocument.load(file);
        //读取pdf文件
        PDFRenderer renderer = new PDFRenderer(doc);
        int pageCount = doc.getNumberOfPages();
        BufferedImage image;
        for (int i = 0; i < pageCount; i++) {
            System.out.println("正在转换" + (i + 1) + "/" + pageCount);
            //96/144/198
            image = renderer.renderImageWithDPI(i, DEFAULT_DPI);
            File filePath = new File(target, (i + 1) + "." + DEFAULT_FORMAT);
            //保存图片
            ImageIO.write(image, DEFAULT_FORMAT, filePath);
        }

        System.out.println("转换结束");
    }
}
