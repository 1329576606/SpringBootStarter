package com.xzx.websocket.exception;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.unix.UnixChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 15:18
 * @description：BaseExceptionListener
 */
@Slf4j
public class BaseExceptionListener implements ExceptionListener {
    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        log.error("onEventException:{}", e);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        log.error("onDisconnectException:{}", e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        log.error("onConnectException:{}", e);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        log.error("onPingException:{}", e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error("exceptionCaught:{}", e);
        forceCloseChannel(ctx);
        return true;
    }

    public static void forceCloseChannel(ChannelHandlerContext ctx) {
        try {
            Channel channel = ctx.pipeline().channel();
            String channelId = channel.id().asShortText();
            if (channel instanceof AbstractNioChannel) {
                AbstractNioChannel.NioUnsafe socketUnsafe = (AbstractNioChannel.NioUnsafe) channel.unsafe();
                log.info(channelId + " force close nio channel");
                socketUnsafe.ch().close();
            } else if (channel instanceof UnixChannel) {
                UnixChannel unixChannel = (UnixChannel) channel;
                log.info(channelId + " force  close unix channel");
                unixChannel.fd().close();
            } else {
                ctx.close();
                log.info(channelId + " force close channel");
            }
        } catch (Exception e) {
            log.info("forceClose error", e);
        }
    }
}
