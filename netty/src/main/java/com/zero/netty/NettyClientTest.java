package com.zero.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 压力测试
public class NettyClientTest {
    public static CountDownLatch latch;
    public static int num = 10;
    public static int round = 1000000;

    public static void main(String[] args) throws Exception {
        latch = new CountDownLatch(num);
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            ExecutorService pool = Executors.newFixedThreadPool(num);
            pool.execute(new PressTask());
        }
        latch.await();
        long end = System.currentTimeMillis();
        double tps = (num*round) / ((end-start)/1000.0);
        System.out.println("测试结束 TPS位" + tps);

    }

    static class PressTask implements Runnable{
        @SneakyThrows
        @Override
        public void run(){
            //客户端需要一个事件循环组
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                //创建客户端启动对象
                //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
                Bootstrap bootstrap = new Bootstrap();
                //设置相关参数
                bootstrap.group(group) //设置线程组
                        .channel(NioSocketChannel.class) // 使用 NioSocketChannel 作为客户端的通道实现
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel channel) throws Exception {
                                //加入处理器
                                channel.pipeline().addLast(new NettyClientTestHandler());
                            }
                        });
                //启动客户端去连接服务器端
                ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();
                System.out.println("netty client start");
                //对关闭通道进行监听
                // channelFuture.channel().closeFuture().sync();
            } finally {
                group.shutdownGracefully();
            }
        }
    }
}
