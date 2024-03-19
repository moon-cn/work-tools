package cn.moon.tool.staticfile;

import cn.moon.WorkTool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

@Slf4j
public class FileServerTool implements WorkTool {
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private ChannelFuture f;
    private JLabel label;

    @Override
    public String getName() {
        return "文件服务器";
    }

    @Override
    public void onToolBtnClick(JPanel panel) {
        log.info("其他用户可以通过浏览器或资源管理器");

        label = new JLabel("请选择路径");
        panel.add(label);

        JButton button = new JButton("选择");
        panel.add(button);

        JButton startBtn = new JButton("启动");
        panel.add(startBtn);
        startBtn.addActionListener(this::start);

        JButton stopBtn = new JButton("停止");
        panel.add(stopBtn);
        stopBtn.addActionListener(this::stop);



        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设置只能选择文件夹

                // 显示文件选择对话框
                int result = fileChooser.showOpenDialog((Component) e.getSource());
                // 判断用户是否点击了选择按钮
                if (result == JFileChooser.APPROVE_OPTION) {
                    path = fileChooser.getSelectedFile().getAbsolutePath();
                    label.setText(path);

                }
            }
        });
    }




    private String path;
    private static final int PORT = 8080;
    ServerBootstrap b;

    public void start(ActionEvent ae) {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(new HttpServerCodec());
                            p.addLast(new HttpObjectAggregator(65536));
                            p.addLast(new ChunkedWriteHandler());
                            p.addLast(new FileServerHandler(path)); // 静态资源处理器
                        }
                    });

            log.info("文件服务器启动成功 {}", PORT);
            f = b.bind(PORT);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(ActionEvent ae) {
        try {
            f.channel().close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        log.info("服务停止");
    }
}
