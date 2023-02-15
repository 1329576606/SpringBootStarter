package com.xzx.websocket.listener;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * @author ：1329576606@qq.com
 * @date ：Created in 2023/2/15 14:35
 * @description：BaseListener
 */
public class BaseListener implements Listener {
    protected SocketIOServer server;

    @Override
    public void listener(SocketIOServer server) {
        this.server = server;
        server.addListeners(this);
    }
}
