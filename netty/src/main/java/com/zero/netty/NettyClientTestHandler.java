package com.zero.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ConcurrentHashMap;

public class NettyClientTestHandler extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<Integer,String> map = new ConcurrentHashMap<>();

    /**
     * 当客户端连接服务器完成就会触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i <  NettyClientTest.round; i++) {
            ByteBuf buf = Unpooled.copiedBuffer("{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）", CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
            map.put(i,"{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）{\"Car_number\":\"京A12345\", \"Brake_state\":1,\"Pic_name\":\"xxxx\", \"Brake_control\":\"open\",\"Display\":\"尊敬的业主，欢迎您回家。\"（大括号）");
            for (int j = 0; j < 15; j++) {
                int b = j + i * 20;
            }
            map.remove(i);
        }
        // 结束计数
        NettyClientTest.latch.countDown();
    }

    //当通道有读取事件时会触发，即服务端发送数据给客户端
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到服务端的消息:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端的地址： " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

